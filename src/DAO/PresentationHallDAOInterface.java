package DAO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Sala all'interno del database.
 */
public interface PresentationHallDAOInterface {
    /**
     * Metodo che restituisce una sala dato il suo identificativo
     * @param presentationHallId
     * @return
     */
    PresentationHallResultInterface getPresentationhallById(int presentationHallId);

    /**
     * Metodo che restituisce tutte le sale memorizzate nel database
     * @return
     */
    ArrayList<PresentationHallResultInterface> getAll();

    /**
     * Metodo che elimina una sala dal database dato il suo identificativo
     * @param id
     * @return
     */
    boolean deletePresentationHallById(int id);

    /**
     * Metodo che restituisce tutte le presentazioni dei libri effettuate in una sala
     * @param presentationHallId
     * @return
     */
    ArrayList<BookPresentationResultInterface> getBookPresentations(int presentationHallId);

    /**
     * Metodo che inserisce una presentazione di un libro in una sala
     * @param book
     * @param presentationHall
     * @param presentationDate
     * @return
     * @throws Exception
     */
    boolean insertBookIntoPresentationHall(String book, int presentationHall, Date presentationDate) throws Exception;

    /**
     * Metodo che elimina una presentazione di un libro da una sala
     * @param book
     * @param presentationHall
     * @return
     */
    boolean deleteBookFromPresentationHall(String book, int presentationHall);

    /**
     * Metodo che inserisce una sala all'interno del database
     * @param name
     * @param address
     * @return
     * @throws Exception
     */
    PresentationHallResultInterface insertPresentationHallInDb(String name, String address) throws Exception;

    /**
     * Metodo che aggiorna una sala dal database dato il suo identificativo
     * @param id
     * @param name
     * @param address
     * @return
     * @throws Exception
     */
    PresentationHallResultInterface updatePresentationHallById(int id, String name, String address) throws Exception;
}