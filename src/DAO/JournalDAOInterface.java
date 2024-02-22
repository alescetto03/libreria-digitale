package DAO;

import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Rivista all'interno del database.
 */
public interface JournalDAOInterface {
    /**
     * Metodo che restituisce una rivista dato il suo issn
     * @param issn
     * @return
     */
    JournalResultInterface getJournalByIssn(String issn);

    /**
     * Metodo che restituisce tutte le riviste memorizzate nel database
     * @return
     */
    ArrayList<JournalResultInterface> getAll();

    /**
     * Metodo che elimina una rivista dal database dato il suo issn
     * @param issn
     * @return
     */
    boolean deleteJournalByIssn(String issn);

    /**
     * Metodo che restituisce tutti gli articoli scientifici pubblicati da una rivista
     * @param issn
     * @return
     */
    ArrayList<ScientificPublicationResultInterface> getPublicationsFromJournal(String issn);

    /**
     * Metodo che inserisce una pubblicazione di un articolo scientifico all'interno di una rivista
     * @param scientificPublication
     * @param journal
     * @return
     */
    boolean insertScientificPublicationIntoJournal(String scientificPublication, String journal);

    /**
     * Metodo che elimina una pubblicazione di un articolo scientifico all'interno di una rivista
     * @param scientificPublication
     * @param journal
     * @return
     */
    boolean deleteScientificPublicationFromJournal(String scientificPublication, String journal);

    /**
     * Metodo che inserisce una rivista all'interno del database
     * @param issn
     * @param name
     * @param argument
     * @param publicationYear
     * @param manager
     * @return
     * @throws Exception
     */
    JournalResultInterface insertJournalInDb(String issn, String name, String argument, int publicationYear, String manager) throws Exception;

    /**
     * Metodo che aggiorna una rivista dal database dato il suo issn
     * @param journalToUpdate
     * @param issn
     * @param name
     * @param argument
     * @param publicationYear
     * @param manager
     * @return
     * @throws Exception
     */
    JournalResultInterface updateJournalByIssn(String journalToUpdate, String issn, String name, String argument, int publicationYear, String manager) throws Exception;
}
