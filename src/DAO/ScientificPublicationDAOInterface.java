package DAO;

import Model.ScientificPublication;

import java.util.ArrayList;

public interface ScientificPublicationDAOInterface {
    ScientificPublicationResultInterface getScientificPublicationByDoi(String doi);

    ArrayList<ScientificPublicationResultInterface> getResearchedPublication(String searchedPublication);

    ArrayList<ScientificPublicationResultInterface> getAll();

    boolean deleteScientificPublicationByDoi(String doi);

    ArrayList<ScientificPublicationResultInterface> getPublicationsFromCollection(int publicationId);

    ScientificPublicationResultInterface updateScientificPublicationByDoi(String publicationToUpdate, String doi, String title, String publisher, ScientificPublication.FruitionMode fruitionMode, int publicationYear, String description) throws Exception;

    ScientificPublicationResultInterface insertPublicationInDb(String doi, String title, String publisher, ScientificPublication.FruitionMode fruition_mode, int publication_year, byte[] cover, String description) throws Exception;

    ArrayList<AuthorResultInterface> getAuthorsOfScientificPublication(String doi);

    boolean insertAuthorOfScientificPublication(int author, String scientificPublication);

    boolean deleteAuthorOfScientificPublication(int author, String scientificPublication);
}
