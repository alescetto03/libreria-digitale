package DAO;

import java.util.ArrayList;

public interface PresentationHallDAOInterface {
    ArrayList<PresentationHallResultInterface> getAll();
    boolean deletePresentationHallById(int id);
}