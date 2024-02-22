package DAO;

/**
 * Interfaccia che rappresenta una tupla della tabella Rivista del database
 */
public interface JournalResultInterface {
    String getIssn();
    String getName();
    String getArgument();
    int getPublicationYear();
    String getManager();
}
