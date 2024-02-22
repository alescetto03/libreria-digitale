package DAO;

/**
 * Interfaccia che rappresenta una tupla della tabella Negozio del database
 */
public interface StoreResultInterface {
    String getPartitaIva();
    String getName();
    String getAddress();
    String getUrl();
}
