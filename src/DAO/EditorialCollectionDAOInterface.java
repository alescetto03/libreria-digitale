package DAO;

import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Collana all'interno del database.
 */
public interface EditorialCollectionDAOInterface {
    /**
     * Metodo che restituisce una collana dato il suo issn
     * @param issn
     * @return
     */
    EditorialCollectionResultInterface getEditorialCollectionByIssn(String issn);

    /**
     * Metodo che restituisce tutte le collane memorizzate nel database
     * @return
     */
    ArrayList<EditorialCollectionResultInterface> getAll();

    /**
     * Metodo che elimina una collana dal database dato il suo issn
     * @param issn
     * @return
     */
    boolean deleteEditorialCollectonByIssn(String issn);

    /**
     * Metodo che restituisce tutti i libri all'interno di una collana
     * @param issn
     * @return
     */
    ArrayList<BookResultInterface> getBooksFromEditorialCollection(String issn);

    /**
     * Metodo che inserisce un libro all'interno di una collana
     * @param book
     * @param editorialCollection
     * @return
     */
    boolean insertBookIntoEditorialCollection(String book, String editorialCollection);

    /**
     * Metodo che elimina un libro all'interno di una collana
     * @param book
     * @param editorialCollection
     * @return
     */
    boolean deleteBookFromEditorialCollection(String book, String editorialCollection);

    /**
     * Metodo che inserisce una collana all'interno del database
     * @param issn
     * @param name
     * @param publisher
     * @return
     * @throws Exception
     */
    EditorialCollectionResultInterface insertEditorialCollectionInDb(String issn, String name, String publisher) throws Exception;

    /**
     * Metodo che aggiorna una collana dal database dato il suo issn
     * @param editorialCollectionToUpdate
     * @param issn
     * @param name
     * @param publisher
     * @return
     * @throws Exception
     */
    EditorialCollectionResultInterface updateEditorialCollectionByIssn(String editorialCollectionToUpdate, String issn, String name, String publisher) throws Exception;
}
