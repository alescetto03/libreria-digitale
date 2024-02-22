package DAO;

/**
 * Interfaccia che rappresenta una tupla della tabella Articolo Scientifico Pubblicazione Rivista del database
 */
public interface PublicationJournalResultInterface {
    String getDoi();

    String getIssn();

    String getPublicationTitle();

    String getJournalName();
}
