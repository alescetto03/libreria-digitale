package DAO;

import java.util.ArrayList;

public interface ConferenceDAOInterface {
    ArrayList<ConferenceResultInterface> getAll();
    boolean deleteConferenceById(int id);
    ConferenceResultInterface insertConferenceInDb(String location, java.sql.Date start_date, java.sql.Date end_date, String organizer, String manager) throws Exception;
}
