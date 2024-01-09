package DAO;

import java.util.ArrayList;

public interface CollectionDAOInterface {
    ArrayList<CollectionResultInterface> getUserPersonalCollections(String username);
    boolean deleteCollectionById(int collectionId);
}
