package DAO;

import java.sql.Date;
import java.util.ArrayList;

public interface ConferenceDAOInterface {
    ConferenceResultInterface getConferenceById(int conferenceId);
    ArrayList<ConferenceResultInterface> getAll();
    boolean deleteConferenceById(int id);
    ConferenceResultInterface insertConferenceInDb(String location, java.sql.Date startDate, java.sql.Date endDate, String organizer, String manager) throws Exception;
    ConferenceResultInterface updateConferenceById(int conferenceId, String location, Date startDate, Date endDate, String organizer, String manager) throws Exception;
    ArrayList<ScientificPublicationPresentationResultInterface> getScientificPublicatonPresentations(int conferenceId);
    boolean insertScientificPublicationPresentation(int conference, String scientificPublication, Date presentationDate) throws Exception;
    boolean deleteScientificPublicationPresentation(int conference, String scientificPublication);
}
