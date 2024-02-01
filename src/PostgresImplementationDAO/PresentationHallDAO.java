package PostgresImplementationDAO;

import DAO.BookPresentationResultInterface;
import DAO.BookResultInterface;
import DAO.PresentationHallDAOInterface;
import DAO.PresentationHallResultInterface;

import java.sql.*;
import java.util.ArrayList;

public class PresentationHallDAO implements PresentationHallDAOInterface {
    @Override
    public PresentationHallResultInterface getPresentationhallById(int presentationHallId) {
        final String query = "SELECT * FROM Sala WHERE cod_sala = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, presentationHallId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new PresentationHallResult(result);
            }
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<PresentationHallResultInterface> getAll() {
        final String query = "SELECT * FROM Sala ORDER BY cod_sala";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<PresentationHallResultInterface> presentationHallResults = new ArrayList<>();
            while(result.next()){
                PresentationHallResultInterface presentationHallResult = new PresentationHallResult(result);
                presentationHallResults.add(presentationHallResult);
            }
            return presentationHallResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deletePresentationHallById(int id) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM sala WHERE cod_sala = ?");
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<BookPresentationResultInterface> getBookPresentations(int presentationHallId) {
        final String query = "SELECT s.cod_sala, s.nome, s.indirizzo, pl.data_presentazione, l.isbn, l.titolo, l.editore, l.modalita_fruizione, l.anno_pubblicazione, l.copertina, l.descrizione, l.genere, l.target, l.materia, l.tipo FROM presentazione_libro AS pl " +
                "JOIN libro AS l ON l.isbn = pl.libro " +
                "JOIN sala s on pl.sala = s.cod_sala " +
                "WHERE pl.sala = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, presentationHallId);
            ResultSet result = statement.executeQuery();

            ArrayList<BookPresentationResultInterface> bookPresentationResults = new ArrayList<>();
            while (result.next()) {
                BookResultInterface bookResult = new BookResult(result);
                PresentationHallResultInterface presentationHallResult = new PresentationHallResult(result);
                BookPresentationResultInterface bookPresentationResult = new BookPresentationResult(presentationHallResult, bookResult, result.getDate("data_presentazione"));
                bookPresentationResults.add(bookPresentationResult);
            }
            return bookPresentationResults;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    public boolean insertBookIntoPresentationHall(String book, int presentationHall, Date presentationDate) throws Exception {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO presentazione_libro VALUES (?,?,?) ON CONFLICT (sala, libro) DO UPDATE SET data_presentazione = ?");
        ) {
            statement.setInt(1, presentationHall);
            statement.setString(2, book);
            statement.setDate(3, presentationDate);
            statement.setDate(4, presentationDate);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getSQLState().equals("23502") && e.getMessage().contains("data_presentazione")) {
                throw new Exception("Inserisci una data di presentazione!");
            }
            return false;
        }
    }
    public boolean deleteBookFromPresentationHall(String book, int presentationHall) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM presentazione_libro WHERE libro = ? AND sala = ?");
        ) {
            statement.setString(1, book);
            statement.setInt(2, presentationHall);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public PresentationHallResultInterface insertPresentationHallInDb(String name, String address) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Sala (nome, indirizzo) VALUES (NULLIF(?, ''), NULLIF(?, '')) RETURNING Sala.*");
        ) {
            statement.setString(1, name);
            statement.setString(2, address);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new PresentationHallResult(result);
        } catch (SQLException e) {
            if (e.getMessage().contains("sala_pk"))
                throw new Exception("Stai inserendo una sala che esiste già.");
            else if (e.getMessage().contains("nome") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"nome\" non può essere vuoto.");
            else if (e.getMessage().contains("indirizzo") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"indirizzo\" non può essere vuoto.");
            else
                throw new Exception("C'è stato un errore durante l'inserimento");
        }
    }

    @Override
    public PresentationHallResultInterface updatePresentationHallById(int id, String name, String address) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("UPDATE Sala SET nome = NULLIF(?, ''), indirizzo = NULLIF(?, '') WHERE cod_sala = ? RETURNING Sala.*");
        ) {
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setInt(3, id);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new PresentationHallResult(result);
        } catch (SQLException e) {
            if (e.getMessage().contains("sala_pk"))
                throw new Exception("Stai inserendo una sala che esiste già.");
            else if (e.getMessage().contains("nome") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"nome\" non può essere vuoto.");
            else if (e.getMessage().contains("indirizzo") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"indirizzo\" non può essere vuoto.");
            else
                throw new Exception("C'è stato un errore durante la modifica.");
        }
    }
}
