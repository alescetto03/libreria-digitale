package PostgresImplementationDAO;

import DAO.BookResultInterface;
import DAO.BookSaleResultInterface;
import DAO.StoreResultInterface;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Vendita del database
 */
public class BookSaleResult implements BookSaleResultInterface {
    private StoreResultInterface store;
    private BookResultInterface book;
    private float price;
    private int quantity;

    public BookSaleResult(StoreResultInterface store, BookResultInterface book, float price, int quantity) {
        this.store = store;
        this.book = book;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public StoreResultInterface getStore() {
        return store;
    }

    @Override
    public BookResultInterface getBook() {
        return book;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
