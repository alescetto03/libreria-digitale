package DAO;

import java.util.ArrayList;

public interface PresentationHallDAOInterface {
    ArrayList<PresentationHallResultInterface> getAll();
    boolean deletePresentationHallById(int id);
    PresentationHallResultInterface insertPresentationHallInDb(String name, String address) throws Exception;
}