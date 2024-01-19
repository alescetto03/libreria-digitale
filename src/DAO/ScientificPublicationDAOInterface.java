package DAO;

import java.util.ArrayList;

public interface ScientificPublicationDAOInterface {

    ArrayList<ScientificPublicationResultInterface> getResearchedPublication(String searchedPublication);

    ArrayList<ScientificPublicationResultInterface> getAll();

    boolean deleteScientificPublicationByDoi(String doi);

    ArrayList<ScientificPublicationResultInterface> getPublicationsFromCollection(int publicationId);
}
