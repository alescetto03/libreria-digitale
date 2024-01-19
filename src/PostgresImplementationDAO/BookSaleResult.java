package PostgresImplementationDAO;

import DAO.BookSaleResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookSaleResult implements BookSaleResultInterface {
    String isbn;
    String partitaIva;
    String bookTitle;
    String storeName;
    float price;
    int quantity;

    public BookSaleResult(String isbn, String partitaIva, String bookTitle, String storeName, float price, int quantity) {
        this.isbn = isbn;
        this.partitaIva = partitaIva;
        this.bookTitle = bookTitle;
        this.storeName = storeName;
        this.price = price;
        this.quantity = quantity;
    }

    public BookSaleResult(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getString("isbn"),
            resultSet.getString("partita_iva"),
            resultSet.getString("titolo_libro"),
            resultSet.getString("nome_negozio"),
            resultSet.getFloat("prezzo"),
            resultSet.getInt("quantita")
        );
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getStoreName() {
        return storeName;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
