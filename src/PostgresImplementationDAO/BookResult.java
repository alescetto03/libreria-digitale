package PostgresImplementationDAO;

import DAO.BookResultInterface;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Libro del database
 */
public class BookResult implements BookResultInterface {
    String isbn;
    String title;
    String publisher;
    String fruition_mode;
    int publication_year;
    InputStream cover;
    String description;
    String book_type;
    String genre;
    String target;
    String topic;

    public BookResult(String isbn, String title, String publisher, String fruition_mode, int publication_year, InputStream cover, String description, String genre, String target, String topic, String book_type) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.fruition_mode = fruition_mode;
        this.publication_year = publication_year;
        this.cover = cover;
        this.description = description;
        this.genre = genre;
        this.target = target;
        this.topic = topic;
        this.book_type = book_type;
    }

    public BookResult(ResultSet result) throws SQLException {
        this(
                result.getString("isbn"),
                result.getString("titolo"),
                result.getString("editore"),
                result.getString("modalita_fruizione"),
                result.getInt("anno_pubblicazione"),
                result.getBinaryStream("copertina"),
                result.getString("descrizione"),
                result.getString("genere"),
                result.getString("target"),
                result.getString("materia"),
                result.getString("tipo")
        );
    }


    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getFruitionMode() {
        return fruition_mode;
    }

    public int getPublicationYear() {
        return publication_year;
    }

    public InputStream getCover() {
        return cover;
    }

    public String getDescription() {
        return description;
    }

    public String getBookType() {
        return book_type;
    }

    public String getGenre() {
        return genre;
    }

    public String getTarget() {
        return target;
    }

    public String getTopic() {
        return topic;
    }
}
