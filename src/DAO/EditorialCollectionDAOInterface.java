package DAO;

import java.util.ArrayList;

public interface EditorialCollectionDAOInterface {
    ArrayList<EditorialCollectionResultInterface> getAll();
    boolean deleteEditorialCollectonByIssn(String issn);
}