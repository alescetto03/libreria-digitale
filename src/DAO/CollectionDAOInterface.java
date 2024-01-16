package DAO;

import Model.Collection;

import java.util.ArrayList;

public interface CollectionDAOInterface {
    ArrayList<CollectionResultInterface> getUserPersonalCollections(String username);
    boolean deleteCollectionById(int collectionId);
    boolean updateCollectionById(int collectionId, String name, Collection.Visibility visibility, String owner);
    boolean insertCollection(String name, Collection.Visibility visibility, String owner);
}
