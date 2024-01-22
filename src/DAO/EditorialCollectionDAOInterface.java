package DAO;

import java.util.ArrayList;

public interface EditorialCollectionDAOInterface {
    ArrayList<EditorialCollectionResultInterface> getAll();
    boolean deleteEditorialCollectonByIssn(String issn);
    EditorialCollectionResultInterface insertEditorialCollectionInDb(String issn, String name, String publisher) throws Exception;
}
