package DAO;

import java.util.ArrayList;

public interface JournalDAOInterface {
    ArrayList<JournalResultInterface> getAll();
    boolean deleteJournalByIssn(String issn);
    ArrayList<PublicationJournalResultInterface> getPublicationsFromJournal();
}
