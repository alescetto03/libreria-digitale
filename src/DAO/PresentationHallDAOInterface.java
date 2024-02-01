package DAO;

import java.sql.Date;
import java.util.ArrayList;

public interface PresentationHallDAOInterface {
    PresentationHallResultInterface getPresentationhallById(int presentationHallId);
    ArrayList<PresentationHallResultInterface> getAll();
    boolean deletePresentationHallById(int id);
    ArrayList<BookPresentationResultInterface> getBookPresentations(int presentationHallId);
    boolean insertBookIntoPresentationHall(String book, int presentationHall, Date presentationDate) throws Exception;
    boolean deleteBookFromPresentationHall(String book, int presentationHall);
    PresentationHallResultInterface insertPresentationHallInDb(String name, String address) throws Exception;
    PresentationHallResultInterface updatePresentationHallById(int id, String name, String address) throws Exception;
}