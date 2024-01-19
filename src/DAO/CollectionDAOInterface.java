package DAO;

import Model.Collection;
import PostgresImplementationDAO.CollectionResult;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface CollectionDAOInterface {

    ArrayList<CollectionResultInterface> getReasearchedCollection(String searchedCollection, String username);
    ArrayList<CollectionResultInterface> getUserPersonalCollections(String username);
    ArrayList<CollectionResultInterface> getUserSavedCollections(String username);
    boolean deleteCollectionById(int collectionId);
    boolean deleteSavedCollectionById(int collectionId, String username);
    CollectionResult updateCollectionById(int collectionId, String name, Collection.Visibility visibility, String owner);
    CollectionResult insertCollection(String name, Collection.Visibility visibility, String owner);
    CollectionResultInterface getAllByCollectionId(int collectionId);
    boolean deleteBookFromCollection(int collectionId, String isbn);
    boolean deletePublicationFromCollection(int collectionId, String doi);
    boolean saveCollectionById(int collectionId, String username);
    boolean isBookInCollection(int collectionId, String bookIsbn);
    boolean isPublicationInCollection(int collectionId, String publicationDoi);
    boolean insertBookInCollection(int collectionId, String bookIsbn);
    boolean insertPublicationInCollection(int collectionId, String publicationDoi);


}
