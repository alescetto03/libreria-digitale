package DAO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Conferenza all'interno del database.
 */
public interface ConferenceDAOInterface {
    /**
     * Metodo che restituisce una conferenza dato il suo identificativo
     * @param conferenceId
     * @return
     */
    ConferenceResultInterface getConferenceById(int conferenceId);

    /**
     * Metodo che restituisce tutte le conferenza memorizzate nel database
     * @return
     */
    ArrayList<ConferenceResultInterface> getAll();

    /**
     * Metodo che elimina una conferenza dal database dato il suo identificativo
     * @param id
     * @return
     */
    boolean deleteConferenceById(int id);

    /**
     * Metodo che inserisce una conferenza all'interno del database
     * @param location
     * @param startDate
     * @param endDate
     * @param organizer
     * @param manager
     * @return
     * @throws Exception
     */
    ConferenceResultInterface insertConferenceInDb(String location, java.sql.Date startDate, java.sql.Date endDate, String organizer, String manager) throws Exception;

    /**
     * Metodo che aggiorna una conferenza dal database dato il suo identificativo
     * @param conferenceId
     * @param location
     * @param startDate
     * @param endDate
     * @param organizer
     * @param manager
     * @return
     * @throws Exception
     */
    ConferenceResultInterface updateConferenceById(int conferenceId, String location, Date startDate, Date endDate, String organizer, String manager) throws Exception;

    /**
     * Metodo che restituisce tutte le presentazioni degli articoli scientifici effettuate durante una conferenza
     * @param conferenceId
     * @return
     */
    ArrayList<ScientificPublicationPresentationResultInterface> getScientificPublicatonPresentations(int conferenceId);

    /**
     * Metodo che inserisce una presentazione di un articolo scientifico durante una conferenza
     * @param conference
     * @param scientificPublication
     * @param presentationDate
     * @return
     * @throws Exception
     */
    boolean insertScientificPublicationPresentation(int conference, String scientificPublication, Date presentationDate) throws Exception;

    /**
     * Metodo che elimina una presentazione di un articolo scientifico durante una conferenza
     * @param conference
     * @param scientificPublication
     * @return
     */
    boolean deleteScientificPublicationPresentation(int conference, String scientificPublication);
}
