package PostgresImplementationDAO;

import DAO.AuthorResultInterface;
import DAO.BookDAOInterface;
import DAO.BookResultInterface;
import Model.Book;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementazione per PostgreSQL dell'interfaccia DAO che gestisce la tabella Libro all'interno del database.
 */
public class BookDAO implements BookDAOInterface {

    /**
     * @inheritDoc
     * @param isbn
     * @return
     */
    public BookResultInterface getBookByIsbn(String isbn) {
        final String query = "SELECT * FROM Libro WHERE isbn = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, isbn);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new BookResult(result);
            }
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
        }
        return null;
    }

    /**
     * @inheritDoc
     * @param searchedBook
     * @return
     */
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

    /**
     * @inheritDoc
     * @return
     */
    @Override
    public ArrayList<BookResultInterface> getAll() {
        final String query = "SELECT * FROM Libro ORDER BY isbn";
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

    /**
     * @inheritDoc
     * @param fruitionMode
     * @return
     */
    public ArrayList<BookResultInterface> getBooksByFruitionMode(Book.FruitionMode fruitionMode) {
        final String query = "SELECT * FROM Libro WHERE modalita_fruizione = ? ORDER BY isbn";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setObject(1, fruitionMode.name().toLowerCase(), Types.OTHER);
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

    /**
     * @inheritDoc
     * @param collectionId
     * @return
     */
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

    /**
     * @inheritDoc
     * @param isbn
     * @return
     */
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

    /**
     * @inheritDoc
     * @param bookToUpdate
     * @param isbn
     * @param title
     * @param publisher
     * @param fruition_mode
     * @param publication_year
     * @param cover
     * @param description
     * @param genre
     * @param target
     * @param topic
     * @param type
     * @return
     * @throws Exception
     */
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
                throw new Exception("Il campo \"titolo\" non può essere vuoto");
            } else if (e.getSQLState().equals("23502") && e.getMessage().contains("editore")) {
                throw new Exception("Il campo \"editore\" non può essere vuoto");
            } else if (type.name().equalsIgnoreCase("didattico") && e.getMessage().contains("didattico_or_romanzo")) {
                throw new Exception("I campi \"target\" e \"materia\" non possono essere vuoti");
            } else if (type.name().equalsIgnoreCase("romanzo") && e.getMessage().contains("didattico_or_romanzo")) {
                throw new Exception("Il campo \"genere\" non può essere vuoto");
            } else {
                throw new Exception("C'è stato un errore durante la modifica");
            }
        }
        return null;
    }

    /**
     * @inheritDoc
     * @param publisher
     * @return
     */
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

    /**
     * @inheritDoc
     * @param isbn
     * @param title
     * @param publisher
     * @param fruition_mode
     * @param publication_year
     * @param cover
     * @param description
     * @param genre
     * @param target
     * @param topic
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public BookResultInterface insertBookInDb(String isbn, String title, String publisher, Book.FruitionMode fruition_mode, int publication_year, byte[] cover, String description, String genre, String target, String topic, Book.BookType type) throws Exception{
        String query;
        if (type.name().equals("ROMANZO")) {
            query = "INSERT INTO Libro (isbn, titolo, editore, modalita_fruizione, anno_pubblicazione, copertina, descrizione, genere, tipo) VALUES(?, NULLIF(?, ''), NULLIF(?, ''), ?, ?, NULLIF(?, ''), NULLIF(?, ''), NULLIF(?, ''), ?) RETURNING *";
        } else {
            query = "INSERT INTO Libro (isbn, titolo, editore, modalita_fruizione, anno_pubblicazione, copertina, descrizione, target, materia, tipo) VALUES(?, NULLIF(?, ''), NULLIF(?, ''), ?, ?, NULLIF(?, ''), NULLIF(?, ''), NULLIF(?, ''), NULLIF(?, ''), ?) RETURNING *";
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
            statement.setBytes(6, cover);
            statement.setString(7, description);
            if (type.name().equals("ROMANZO")) {
                statement.setString(8, genre);
                statement.setObject(9, type.name().toLowerCase(), Types.OTHER);
            } else {
                statement.setString(8, target);
                statement.setString(9, topic);
                statement.setObject(10, type.name().toLowerCase(), Types.OTHER);
            }
            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new BookResult(result);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            if (exception.getMessage().contains("isbn_check"))
                throw new Exception("Un ISBN deve essere una sequenza numerica di 13 o 10 cifre che inizia per '978' o per '979' e non puo' essere nullo.");
            else if (exception.getMessage().contains("didattico_or_romanzo") && type.name().equalsIgnoreCase("didattico"))
                throw new Exception("I campi \"target\" e \"materia\" non possono essere vuoti");
            else if (exception.getMessage().contains("didattico_or_romanzo") && type.name().equalsIgnoreCase("romanzo"))
                throw new Exception("Il campo \"genere\" non può essere vuoto");
            else if (exception.getMessage().contains("libro_pk"))
                throw new Exception("Esiste già un libro con quell'ISBN!");
            else if (exception.getMessage().contains("titolo") && exception.getSQLState().equals("23502"))
                throw new Exception("Il campo \"titolo\" non può essere vuoto.");
            else if (exception.getMessage().contains("editore") && exception.getSQLState().equals("23502"))
                throw new Exception("Il campo \"editore\" non può essere vuoto.");
             else
                throw new Exception("C'è stato un errore durante l'inserimento!");
        }
    }

    /**
     * @inheritDoc
     * @param isbn
     * @return
     */
    public ArrayList<AuthorResultInterface> getAuthorsOfBook(String isbn) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT cod_autore, nome, data_nascita, data_morte, nazionalita, biografia " +
                                                                        "FROM autore JOIN scrittura_libro sl on autore.cod_autore = sl.autore WHERE libro = ?");
        ) {
            statement.setString(1, isbn);
            ResultSet result = statement.executeQuery();

            ArrayList<AuthorResultInterface> authorResults = new ArrayList<>();
            while (result.next()) {
                AuthorResultInterface authorResult = new AuthorResult(result);
                authorResults.add(authorResult);
            }
            return authorResults;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    /**
     * @inheritDoc
     * @param author
     * @param book
     * @return
     */
    public boolean insertAuthorOfBook(int author, String book) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO scrittura_libro VALUES (?,?)");
        ) {
            statement.setInt(1, author);
            statement.setString(2, book);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @inheritDoc
     * @param author
     * @param book
     * @return
     */
    public boolean deleteAuthorOfBook(int author, String book) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM scrittura_libro WHERE autore = ? AND libro = ?");
        ) {
            statement.setInt(1, author);
            statement.setString(2, book);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
