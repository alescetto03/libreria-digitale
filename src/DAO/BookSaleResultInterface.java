package DAO;

/**
 * Interfaccia che rappresenta una tupla della tabella Vendita del database
 */
public interface BookSaleResultInterface {
    StoreResultInterface getStore();
    BookResultInterface getBook();
    float getPrice();
    int getQuantity();
}
