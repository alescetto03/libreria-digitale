package PostgresImplementationDAO;

import DAO.BookResultInterface;
import DAO.PresentationHallDAOInterface;
import DAO.PresentationHallResultInterface;

import java.sql.*;
import java.time.LocalDate;
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
        final String query = "SELECT * FROM Sala";
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
    public ArrayList<BookResultInterface> getPresentedBooks(int presentationHallId) {
        final String query = "SELECT l.isbn, l.titolo, l.editore, l.modalita_fruizione, l.anno_pubblicazione, l.copertina, l.descrizione, l.genere, l.target, l.materia, l.tipo FROM presentazione_libro AS pl " +
                "JOIN libro AS l ON l.isbn = pl.libro " +
                "WHERE pl.sala = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, presentationHallId);
            ResultSet result = statement.executeQuery();

            ArrayList<BookResultInterface> bookResults = new ArrayList<>();
            while (result.next()) {
                BookResultInterface booksResult = new BookResult(result);
                bookResults.add(booksResult);
            }
            return bookResults;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    public boolean insertBookIntoPresentationHall(String book, int presentationHall, LocalDate presentationDate) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO presentazione_libro VALUES (?,?,?)");
        ) {
            statement.setInt(1, presentationHall);
            statement.setString(2, book);
            statement.setDate(3, Date.valueOf(presentationDate));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
}
