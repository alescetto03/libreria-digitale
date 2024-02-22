package DAO;

import Model.ScientificPublication;

import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Articolo Scientifico all'interno del database.
 */
public interface ScientificPublicationDAOInterface {
    /**
     * Metodo che restituisce un libro dato il suo doi
     * @param doi
     * @return
     */
    ScientificPublicationResultInterface getScientificPublicationByDoi(String doi);

    /**
     * Metodo che cerca e restituisce un articolo scientifico in base ad una parola chiave contenuta nel titolo
     * @param searchedPublication
     * @return
     */
    ArrayList<ScientificPublicationResultInterface> getResearchedPublication(String searchedPublication);

    /**
     * Metodo che restituisce tutti gli articoli scientifici memorizzati nel database
     * @return
     */
    ArrayList<ScientificPublicationResultInterface> getAll();

    /**
     * Metodo che elimina un articolo scientifico dal database dato il suo doi
     * @param doi
     * @return
     */
    boolean deleteScientificPublicationByDoi(String doi);

    /**
     * Metodo che restituisce tutti gli articoli scientifici da una raccolta
     * @param publicationId
     * @return
     */
    ArrayList<ScientificPublicationResultInterface> getPublicationsFromCollection(int publicationId);

    /**
     * Metodo che aggiorna un articolo scientifico dal database dato il suo doi
     * @param publicationToUpdate
     * @param doi
     * @param title
     * @param publisher
     * @param fruitionMode
     * @param publicationYear
     * @param description
     * @return
     * @throws Exception
     */
    ScientificPublicationResultInterface updateScientificPublicationByDoi(String publicationToUpdate, String doi, String title, String publisher, ScientificPublication.FruitionMode fruitionMode, int publicationYear, String description) throws Exception;

    /**
     * Metodo che inserisce un articolo scientifico all'interno del database
     * @param doi
     * @param title
     * @param publisher
     * @param fruition_mode
     * @param publication_year
     * @param cover
     * @param description
     * @return
     * @throws Exception
     */
    ScientificPublicationResultInterface insertPublicationInDb(String doi, String title, String publisher, ScientificPublication.FruitionMode fruition_mode, int publication_year, byte[] cover, String description) throws Exception;

    /**
     * Metodo che restituisce gli autori di un articolo scientifico
     * @param doi
     * @return
     */
    ArrayList<AuthorResultInterface> getAuthorsOfScientificPublication(String doi);

    /**
     * Metodo che inserisce all'interno del database un'istanza della relazione tra articolo scientifico e autore
     * @param author
     * @param scientificPublication
     * @return
     */
    boolean insertAuthorOfScientificPublication(int author, String scientificPublication);

    /**
     * Metodo che elimina dal database un'istanza della relazione tra articolo scientifico e autore
     * @param author
     * @param scientificPublication
     * @return
     */
    boolean deleteAuthorOfScientificPublication(int author, String scientificPublication);
}
