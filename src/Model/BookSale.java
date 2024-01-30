package Model;

import java.util.HashMap;
import java.util.Map;

public class BookSale extends AbstractModel {
    private Store store;
    private Book book;
    private float price;
    private int quantity;

    public BookSale(Store store, Book book, float price, int quantity) {
        this.store = store;
        this.book = book;
        this.price = price;
        this.quantity = quantity;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("store", getStore().getData());
        data.put("book", getBook().getData());
        data.put("price", getPrice());
        data.put("quantity", getQuantity());
        return data;
    }
}
