package DAO;

import PostgresImplementationDAO.ScientificPublicationResult;

import java.util.ArrayList;

public interface ScientificPublicationDAOInterface {

    ArrayList<ScientificPublicationResultInterface> getResearchedPublication(String searchedPublication);

    ArrayList<ScientificPublicationResultInterface> getAll();

    boolean deleteScientificPublicationByDoi(String doi);
}
