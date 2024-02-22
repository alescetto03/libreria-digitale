package DAO;


import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Negozio all'interno del database.
 */
public interface StoreDAOInterface {
    /**
     * Metodo che restituisce un negozio data la sua partita IVA.
     * @param partitaIva
     * @return
     */
    StoreResultInterface getStoreByPartitaIva(String partitaIva);

    /**
     * Metodo che restituisce tutti i negozi che vendono una serie completa
     * @param searchedSerie
     * @return
     */
    ArrayList<StoreResultInterface> storeCompleteSerie(String searchedSerie);

    /**
     * Metodo che restituisce tutti i negozi memorizzati nel database
     * @return
     */
    ArrayList<StoreResultInterface> getAll();

    /**
     * Metodo che elimina un negozio dal database data la sua partita IVA
     * @param partitaIva
     * @return
     */
    boolean deleteStoreByPartitaIva(String partitaIva);

    /**
     * Metodo che aggiorna un negozio dal database dato la sua partita IVA
     * @param storeToUpdate
     * @param partitaIva
     * @param name
     * @param address
     * @param url
     * @return
     * @throws Exception
     */
    StoreResultInterface updateStoreByPartitaIva(String storeToUpdate, String partitaIva, String name, String address, String url) throws Exception;

    /**
     * Metodo che inserisce un negozio all'interno del database
     * @param partitaIva
     * @param name
     * @param address
     * @param url
     * @return
     * @throws Exception
     */
    StoreResultInterface insertStoreInDb(String partitaIva, String name, String address, String url) throws Exception;

    /**
     * Metodo che restituisce tutte le vendite di libri effettuate da un negozio
     * @param partitaIva
     * @return
     */
    ArrayList<BookSaleResultInterface> getBookSales(String partitaIva);

    /**
     * Metodo che inserisce una vendita di un libro effettuata da un negozio
     * @param book
     * @param store
     * @param price
     * @param quantity
     * @return
     * @throws Exception
     */
    boolean insertBookSale(String book, String store, double price, int quantity) throws Exception;

    /**
     * Metodo che rimuove una vendita di un libro effettuata da un negozio
     * @param book
     * @param store
     * @return
     * @throws Exception
     */
    boolean deleteBookSale(String book, String store) throws Exception;
}
