package PostgresImplementationDAO;

import DAO.AuthorDAOInterface;
import DAO.AuthorResultInterface;
import DAO.BookResultInterface;

import java.sql.*;
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
//            System.out.println("MESSAGE:" + e.getMessage());
//            System.out.println("SQL STATE:" + e.getSQLState());
//            System.out.println("ERROR CODE:" + e.getErrorCode());
//            System.out.println("LOCALIZED MESSAGE:" + e.getLocalizedMessage());
//            System.out.println("NEXT EXCEPTION:" + e.getNextException());
//            System.out.println("CAUSE:" + e.getCause());
//            System.out.println("STACK TRACE:" + e.getStackTrace());

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
        //return null;
    }


    public AuthorResultInterface updateAuthorById (int author_id, String name, java.sql.Date birth_date, java.sql.Date death_date, String nationality, String bio) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("UPDATE Autore SET nome = NULLIF(?, ''), data_nascita = ?, data_morte = ?, nazionalita = NULLIF(?, ''), biografia = NULLIF(?, '') WHERE cod_autore = ? RETURNING *");
        ) {
            statement.setString(1, name);
            statement.setDate(2, birth_date);
            statement.setDate(3, death_date);
            statement.setString(4, nationality);
            statement.setString(5, bio);
            statement.setInt(6, author_id);

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
