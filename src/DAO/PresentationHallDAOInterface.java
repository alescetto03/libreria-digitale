package DAO;

import java.time.LocalDate;
import java.util.ArrayList;

public interface PresentationHallDAOInterface {
    PresentationHallResultInterface getPresentationhallById(int presentationHallId);
    ArrayList<PresentationHallResultInterface> getAll();
    boolean deletePresentationHallById(int id);
    ArrayList<BookResultInterface> getPresentedBooks(int presentationHallId);
    boolean insertBookIntoPresentationHall(String book, int presentationHall, LocalDate presentationDate);
    boolean deleteBookFromPresentationHall(String book, int presentationHall);
    PresentationHallResultInterface insertPresentationHallInDb(String name, String address) throws Exception;
    PresentationHallResultInterface updatePresentationHallById(int id, String name, String address) throws Exception;
}