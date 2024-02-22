package DAO;

import Model.Collection;

import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Raccolta all'interno del database.
 */
public interface CollectionDAOInterface {
    /**
     *  Metodo che cerca e restituisce una raccolta in base ad una parola chiave contenuta nel nome
     * @param searchedCollection
     * @param username
     * @return
     */
    ArrayList<CollectionResultInterface> getReasearchedCollection(String searchedCollection, String username);

    /**
     * Metodo che restituisce tutte le raccolte personali di un utente
     * @param username
     * @return
     */
    ArrayList<CollectionResultInterface> getUserPersonalCollections(String username);

    /**
     * Metodo che restituisce tutte le raccolte salvate da un utente
     * @param username
     * @return
     */
    ArrayList<CollectionResultInterface> getUserSavedCollections(String username);

    /**
     * Metodo che elimina una raccolta dal database dato il suo identificativo
     * @param collectionId
     * @return
     */
    boolean deleteCollectionById(int collectionId);

    /**
     * Metodo che elimina dal database un'istanza della relazione tra utente e raccolte (utente_salvataggio_raccolta)
     * @param collectionId
     * @param username
     * @return
     */
    boolean deleteSavedCollectionById(int collectionId, String username);

    /**
     * Metodo che aggiorna una raccolta dal database dato il suo identificativo
     * @param collectionId
     * @param name
     * @param visibility
     * @param owner
     * @return
     */
    CollectionResultInterface updateCollectionById(int collectionId, String name, Collection.Visibility visibility, String owner);

    /**
     * Metodo che inserisce una raccolta all'interno del database
     * @param name
     * @param visibility
     * @param owner
     * @return
     */
    CollectionResultInterface insertCollection(String name, Collection.Visibility visibility, String owner);

    /**
     * Metodo che restituisce una raccolta dal database dato il suo identificativo
     * @param collectionId
     * @return
     */
    CollectionResultInterface getCollectionById(int collectionId);

    /**
     * Metodo che rimuove un libro da una raccolta
     * @param collectionId
     * @param isbn
     * @return
     */
    boolean deleteBookFromCollection(int collectionId, String isbn);

    /**
     * Metodo che rimuove un articolo scientifico da una raccolta
     * @param collectionId
     * @param doi
     * @return
     */
    boolean deletePublicationFromCollection(int collectionId, String doi);

    /**
     * Metodo che inserisce all'interno del database un'istanza della relazione tra utente e raccolta (utente_salvatggio_raccolta)
     * @param collectionId
     * @param username
     * @return
     */
    boolean saveCollectionById(int collectionId, String username);

    /**
     * Metodo che verifica se un libro è presente in una raccolta all'interno del database
     * @param collectionId
     * @param bookIsbn
     * @return
     */
    boolean isBookInCollection(int collectionId, String bookIsbn);

    /**
     * Metodo che verifica se un articolo scientifico è presente in una raccolta all'interno del database
     * @param collectionId
     * @param publicationDoi
     * @return
     */
    boolean isPublicationInCollection(int collectionId, String publicationDoi);

    /**
     * Metodo che inserisce un libro in una raccolta
     * @param collectionId
     * @param bookIsbn
     * @return
     */
    boolean insertBookInCollection(int collectionId, String bookIsbn);

    /**
     * Metodo che inserisce un articolo scientifico in una raccolta
     * @param collectionId
     * @param publicationDoi
     * @return
     */
    boolean insertPublicationInCollection(int collectionId, String publicationDoi);
}
