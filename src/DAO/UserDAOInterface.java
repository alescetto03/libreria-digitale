package DAO;

import java.sql.Date;

/**
 * Interfaccia DAO che gestisce la tabella Utente all'interno del database.
 */
public interface UserDAOInterface {
    /**
     * Metodo che restituisce un utente all'interno del database in base alle sue credenziali
     * @param username
     * @param password
     * @return
     */
    UserResultInterface login(String username, byte[] password);

    /**
     * Metodo che registra un nuovo utente all'interno del database dell'applicativo
     * @param username
     * @param email
     * @param password
     * @param name
     * @param surname
     * @param birthdate
     * @return
     */
    UserResultInterface register(String username, String email, byte[] password, String name, String surname, Date birthdate);
}
