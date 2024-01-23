package PostgresImplementationDAO;

import DAO.AuthorDAOInterface;
import DAO.AuthorResultInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AuthorDAO implements AuthorDAOInterface {
    public ArrayList<AuthorResultInterface> getAll() {
        final String query = "SELECT * FROM Autore";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<AuthorResultInterface> authorResults = new ArrayList<>();
            while(result.next()){
                AuthorResultInterface authorResult = new AuthorResult(result);
                authorResults.add(authorResult);
            }
            return authorResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteAuthorById(int id) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM autore WHERE cod_autore = ?");
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public AuthorResultInterface updateAuthorById(int authorToUpdate, String name, LocalDate birthDate, LocalDate deathDate, String nationality, String biography) throws Exception {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("UPDATE autore SET nome = ?, data_nascita = ?, data_morte = ?, nazionalita = ?, biografia = ? WHERE cod_autore = ?");
        ) {
            statement.setInt(1, authorToUpdate);
            statement.setString(2, name);
            statement.setDate(3, Date.valueOf(birthDate));
            statement.setDate(4, Date.valueOf(deathDate));
            statement.setString(5, nationality);
            statement.setString(6, biography);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                return new AuthorResult(result);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("coerenza_date_autore"))
                throw new Exception("La data di morte di un autore deve essere successiva a quella di nascita.");
            else if (e.getMessage().contains("nome") && e.getMessage().contains("null value"))
                throw new Exception("Attenzione, il campo \"nome\" non può essere vuoto");
            else if (e.getMessage().contains("data_nascita") && e.getMessage().contains("null value"))
                throw new Exception("Attenzione, il campo \"data di nascita\" non può essere vuoto");
            else if (e.getMessage().contains("nazionalita") && e.getMessage().contains("null value"))
                throw new Exception("Attenzione, il campo \"nazionalità\" non può essere vuoto");
            else
                throw new Exception("C'è stato un errore durante la modifica");
        }
        return null;
    }

    @Override
    public AuthorResultInterface insertAuthorInDb(String name, Date birth_date, Date death_date, String nationality, String bio) throws Exception {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Autore(nome, data_nascita, data_morte, nazionalita, biografia) VALUES (NULLIF(?, ''), ?, ?, NULLIF(?, ''), NULLIF(?, '')) RETURNING Autore.*");
        ) {
            statement.setString(1, name);
            statement.setDate(2, birth_date);
            statement.setDate(3, death_date);
            statement.setString(4, nationality);
            statement.setString(5, bio);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new AuthorResult(result);
        } catch (SQLException e) {
            if (e.getMessage().contains("coerenza_date_autore"))
                throw new Exception("La data di morte di un autore deve essere dopo quella di nascita.");
            else if (e.getMessage().contains("autore_pk"))
                throw new Exception("Stai cercando di inserire un autore gia' esistente.");
            else if (e.getMessage().contains("nome") && e.getMessage().contains("null value"))
                throw new Exception("Il nome non puo' essere nullo.");
            else if (e.getMessage().contains("data_nascita") && e.getMessage().contains("null value"))
                throw new Exception("La data di nascita non puo' essere nulla.");
            else if (e.getMessage().contains("nazionalita") && e.getMessage().contains("null value"))
                throw new Exception("La nazionalita' non puo' essere nulla.");
            else
                throw new Exception("General Error For Author.");
        }
    }
}
