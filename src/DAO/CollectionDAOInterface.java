package DAO;

import Model.Collection;

import java.util.ArrayList;

public interface CollectionDAOInterface {

    ArrayList<CollectionResultInterface> getReasearchedCollection(String searchedCollection);
    ArrayList<CollectionResultInterface> getUserPersonalCollections(String username);
    ArrayList<CollectionResultInterface> getUserSavedCollections(String username);
    boolean deleteCollectionById(int collectionId);
    public boolean deleteSavedCollectionById(int collectionId, String username);
    boolean updateCollectionById(int collectionId, String name, Collection.Visibility visibility, String owner);
    boolean insertCollection(String name, Collection.Visibility visibility, String owner);

}
