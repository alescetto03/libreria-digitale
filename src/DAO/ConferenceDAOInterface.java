package DAO;

import java.util.ArrayList;

public interface ConferenceDAOInterface {
    ArrayList<ConferenceResultInterface> getAll();
    boolean deleteConferenceById(int id);
}