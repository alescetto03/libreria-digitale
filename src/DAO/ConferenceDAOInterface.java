package DAO;

import java.sql.Date;
import java.util.ArrayList;

public interface ConferenceDAOInterface {
    ArrayList<ConferenceResultInterface> getAll();
    boolean deleteConferenceById(int id);
    ConferenceResultInterface insertConferenceInDb(String location, java.sql.Date startDate, java.sql.Date endDate, String organizer, String manager) throws Exception;
    ConferenceResultInterface updateConferenceById(int conferenceId, String location, Date startDate, Date endDate, String organizer, String manager) throws Exception;
}
