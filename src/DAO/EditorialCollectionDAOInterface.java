package DAO;

import java.util.ArrayList;

public interface EditorialCollectionDAOInterface {
    EditorialCollectionResultInterface getEditorialCollectionByIssn(String issn);
    ArrayList<EditorialCollectionResultInterface> getAll();
    boolean deleteEditorialCollectonByIssn(String issn);
    ArrayList<BookResultInterface> getBooksFromEditorialCollection(String issn);
    boolean insertBookIntoEditorialCollection(String book, String editorialCollection);
    boolean deleteBookFromEditorialCollection(String book, String editorialCollection);
    EditorialCollectionResultInterface insertEditorialCollectionInDb(String issn, String name, String publisher) throws Exception;
    EditorialCollectionResultInterface updateEditorialCollectionByIssn(String issn, String issnToUpdate, String name, String publisher) throws Exception;
}
