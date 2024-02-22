package DAO;

/**
 * Interfaccia che rappresenta una tupla della tabella Raccolta del database
 */
public interface CollectionResultInterface {

    int getId();
    String getName();
    String getOwner();
    String getVisibility();
}
