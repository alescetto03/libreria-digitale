package DAO;


import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Notifica all'interno del database.
 */
public interface NotificationDAOInterface {
    /**
     * Metodo che restituisce tutte le notifiche ricevute da un utente
     * @param username
     * @return
     */
    ArrayList<NotificationResultInterface> getUserNotification(String username);
}
