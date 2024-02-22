package DAO;

import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Autore all'interno del database.
 */
public interface AuthorDAOInterface {
    /**
     * Metodo che restituisce tutti gli autori memorizzati nel database
     * @return
     */
    ArrayList<AuthorResultInterface> getAll();

    /**
     * Metodo che elimina un autore dal database dato il suo identificativo
     * @param id
     * @return
     */
    boolean deleteAuthorById(int id);

    /**
     * Metodo che aggiorna un autore dal database dato il suo identificativo
     * @param authorToUpdate
     * @param name
     * @param birthDate
     * @param deathDate
     * @param nationality
     * @param biography
     * @return
     * @throws Exception
     */
    AuthorResultInterface updateAuthorById(int authorToUpdate, String name, java.sql.Date birthDate, java.sql.Date deathDate, String nationality, String biography) throws Exception;

    /**
     * Metodo che inserisce un autore all'interno del database
     * @param name
     * @param birth_date
     * @param death_date
     * @param nationality
     * @param bio
     * @return
     * @throws Exception
     */
    AuthorResultInterface insertAuthorInDb (String name, java.sql.Date birth_date, java.sql.Date death_date, String nationality, String bio) throws Exception;
}
