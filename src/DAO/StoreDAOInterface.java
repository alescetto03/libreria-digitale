package DAO;


import java.util.ArrayList;

public interface StoreDAOInterface {
    StoreResultInterface getStoreByPartitaIva(String partitaIva);
    ArrayList<StoreResultInterface> storeCompleteSerie(String searchedSerie);
    ArrayList<StoreResultInterface> getAll();
    boolean deleteStoreByPartitaIva(String partitaIva);
    StoreResultInterface updateStoreByPartitaIva(String storeToUpdate, String partitaIva, String name, String address, String url) throws Exception;
    StoreResultInterface insertStoreInDb(String partitaIva, String name, String address, String url) throws Exception;
    ArrayList<BookSaleResultInterface> getBookSales(String partitaIva);
    boolean insertBookSale(String book, String store, float price, int quantity) throws Exception;
    boolean deleteBookSale(String book, String store) throws Exception;
}
