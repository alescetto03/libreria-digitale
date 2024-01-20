package DAO;

import java.util.ArrayList;

public interface JournalDAOInterface {
    JournalResultInterface getJournalByIssn(String issn);
    ArrayList<JournalResultInterface> getAll();
    boolean deleteJournalByIssn(String issn);
    ArrayList<ScientificPublicationResultInterface> getPublicationsFromJournal(String issn);
    boolean insertScientificPublicationIntoJournal(String scientificPublication, String journal);
    boolean deleteScientificPublicationFromJournal(String scientificPublication, String journal);
}
