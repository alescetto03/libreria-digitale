package PostgresImplementationDAO;

import DAO.BookEditorialCollectionResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookEditorialCollectionResult implements BookEditorialCollectionResultInterface {
    String isbn;
    String issn;
    String bookTitle;
    String collectionName;
    public BookEditorialCollectionResult(String isbn, String bookTitle, String issn, String collectionName) throws SQLException {
        this.isbn = isbn;
        this.issn = issn;
        this.bookTitle = bookTitle;
        this.collectionName = collectionName;
    }

    public BookEditorialCollectionResult(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getString("isbn"),
            resultSet.getString("issn"),
            resultSet.getString("titolo_libro"),
            resultSet.getString("nome_collana")
        );
    }

    public String getIsbn() {
        return isbn;
    }

    public String getIssn() {
        return issn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getCollectionName() {
        return collectionName;
    }
}
