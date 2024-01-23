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
    public BookResultInterface updateBookByIsbn(String bookToUpdate, String isbn, String title, String publisher, Book.FruitionMode fruition_mode, int publication_year, byte[] cover, String description, String genre, String target, String topic, Book.BookType type) throws Exception {
        String query;
        if (type.name().equals("ROMANZO")) {
             query = "UPDATE libro SET isbn = ?, titolo = NULLIF(?, ''), editore = NULLIF(?, ''), modalita_fruizione = ?, anno_pubblicazione = ?, descrizione = NULLIF(?, ''), tipo = ?, copertina = ?, genere = NULLIF(?, '') WHERE isbn = ? RETURNING Libro.*";
        } else {
             query = "UPDATE libro SET isbn = ?, titolo = NULLIF(?, ''), editore = NULLIF(?, ''), modalita_fruizione = ?, anno_pubblicazione = ?, descrizione = NULLIF(?, ''), tipo = ?, copertina = ?, target = NULLIF(?, ''), materia = NULLIF(?, '') WHERE isbn = ? RETURNING Libro.*";
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
                statement.setString(10, bookToUpdate);
            } else {
                statement.setString(9, target);
                statement.setString(10, topic);
                statement.setString(11, bookToUpdate);
            }
            statement.execute();

            ResultSet result = statement.getResultSet();
            if (result.next()) {
                return new BookResult(result);
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage() + "Codice: " + e.getSQLState());
            if (e.getMessage().contains("libro_pk")) {
                throw new Exception("Esiste già un libro con quell'ISBN!");
            } else if (e.getMessage().contains("isbn_check")) {
                throw new Exception("Un ISBN deve essere una sequenza numerica di 13 o 10 cifre che inizia per \'978\' o per \'979\'");
            } else if (e.getSQLState().equals("23502") && e.getMessage().contains("titolo")) {
                throw new Exception("Attenzione, il campo \"titolo\" non può essere vuoto");
            } else if (e.getSQLState().equals("23502") && e.getMessage().contains("editore")) {
                throw new Exception("Attenzione, il campo \"editore\" non può essere vuoto");
            } else if (e.getSQLState().equals("23502") && e.getMessage().contains("descrizione")) {
                throw new Exception("Attenzione, il campo \"descrizione\" non può essere vuoto");
            } else {
                throw new Exception("C'è stato un errore durante la modifica");
            }
        }
        return null;
    }

    public ArrayList<BookResultInterface> getBooksByPublisher(String publisher) {
        final String query = "SELECT * FROM Libro WHERE editore = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, publisher);
            ResultSet result = statement.executeQuery();

            ArrayList<BookResultInterface> bookResults = new ArrayList<>();
            while(result.next()){
                BookResultInterface bookResult = new BookResult(result);
                bookResults.add(bookResult);
            }
            return bookResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
        }
        return null;
    }
}
