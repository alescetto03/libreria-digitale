package DAO;


import java.util.ArrayList;

public interface StoreDAOInterface {
    ArrayList<StoreResultInterface> storeCompleteSerie(String searchedSerie);
    ArrayList<StoreResultInterface> getAll();
    boolean deleteStoreByPartitaIva(String partitaIva);
    StoreResultInterface updateStoreByPartitaIva(String storeToUpdate, String partitaIva, String name, String address, String url) throws Exception;
    StoreResultInterface insertStoreInDb(String partita_iva, String name, String address, String url) throws Exception;
    StoreResultInterface updateStore(String partita_iva, String old_partita_iva, String name, String address, String url) throws Exception;
}
