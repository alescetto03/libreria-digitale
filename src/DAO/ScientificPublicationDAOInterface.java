package DAO;

import PostgresImplementationDAO.ScientificPublicationResult;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface ScientificPublicationDAOInterface {

    ArrayList<ScientificPublicationResultInterface> getResearchedPublication(String searchedPublication);
    ArrayList<ScientificPublicationResultInterface> getPublicationsFromCollection(int publicationId);
}
