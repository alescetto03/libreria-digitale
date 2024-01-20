package PostgresImplementationDAO;

import DAO.BookDAOInterface;
import DAO.BookResultInterface;
import Model.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO implements BookDAOInterface {
    @Override
    public ArrayList<BookResultInterface> getResearchedBook(String searchedBook){
        final String query = "SELECT * FROM Libro WHERE Libro.titolo ILIKE '%'|| ? ||'%'";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
            ) {
                statement.setString(1, searchedBook);
                ResultSet result = statement.executeQuery();

                ArrayList<BookResultInterface> searchedBookList = new ArrayList<BookResultInterface>();
                while(result.next()){
                    BookResult book = new BookResult(result);
                    searchedBookList.add(book);
                }

                return searchedBookList;
            }catch (SQLException e){
                System.out.println("Errore: " + e.getMessage());
                return null;
        }
    }

    @Override
    public ArrayList<BookResultInterface> getAll() {
        final String query = "SELECT * FROM Libro";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<BookResultInterface> bookResults = new ArrayList<>();
            while(result.next()){
                BookResultInterface bookResult = new BookResult(result);
                bookResults.add(bookResult);
            }
            return bookResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<BookResultInterface> getBooksFromCollection(int collectionId) {
        final String query = "SELECT l.isbn, l.titolo, l.editore, l.modalita_fruizione, l.anno_pubblicazione, l.copertina, l.descrizione, l.genere, l.target, l.materia, l.tipo " +
                             "FROM Libro AS l JOIN Libro_Contenuto_Raccolta AS lr ON l.isbn = lr.libro " +
                             "WHERE lr.raccolta = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, collectionId);
            ResultSet result = statement.executeQuery();

            ArrayList<BookResultInterface> booksInCollection = new ArrayList<BookResultInterface>();
            while(result.next()){
                BookResult book = new BookResult(result);
                booksInCollection.add(book);
            }

            return booksInCollection;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteBookByIsbn(String isbn) {
        try (
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM libro WHERE isbn = ?");
        ) {
            statement.setString(1, isbn);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }

    @Override
    public BookResultInterface updateBookByIsbn(String isbn, String title, String publisher, Book.FruitionMode fruition_mode, int publication_year, byte[] cover, String description, String genre, String target, String topic, Book.BookType type) throws Exception {
        String query;
        if (type.name().equals("ROMANZO")) {
             query = "UPDATE libro SET isbn = ?, titolo = ?, editore = ?, modalita_fruizione = ?, anno_pubblicazione = ?, descrizione = ?, tipo = ?, copertina = ?, genere = ? WHERE isbn = ? RETURNING *";
        } else {
             query = "UPDATE libro SET isbn = ?, titolo = ?, editore = ?, modalita_fruizione = ?, anno_pubblicazione = ?, descrizione = ?, tipo = ?, copertina = ?, target = ?, materia = ? WHERE isbn = ? RETURNING *";
        }
        try (
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
        ) {
            statement.setString(1, isbn);
            statement.setString(2, title);
            statement.setString(3, publisher);
            statement.setObject(4, fruition_mode.name().toLowerCase(), Types.OTHER);
            statement.setInt(5, publication_year);
            statement.setString(6, description);
            statement.setObject(7, type.name().toLowerCase(), Types.OTHER);
            statement.setBytes(8, cover);
            if (type.name().equals("ROMANZO")) {
                statement.setString(9, genre);
                statement.setString(10, isbn);
            } else {
                statement.setString(9, target);
                statement.setString(10, topic);
                statement.setString(11, isbn);
            }
            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new BookResult(result);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
