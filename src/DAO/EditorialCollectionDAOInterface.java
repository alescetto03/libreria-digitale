package DAO;

import java.util.ArrayList;

public interface EditorialCollectionDAOInterface {
    EditorialCollectionResultInterface getEditorialCollectionByIssn(String issn);
    EditorialCollectionResultInterface updateEditorialCollectionByIssn(String editorialCollectionToUpdate, String issn, String name, String publisher);
    ArrayList<EditorialCollectionResultInterface> getAll();
    boolean deleteEditorialCollectonByIssn(String issn);
    ArrayList<BookResultInterface> getBooksFromEditorialCollection(String issn);
    boolean insertBookIntoEditorialCollection(String book, String editorialCollection);
    boolean deleteBookFromEditorialCollection(String book, String editorialCollection);
    EditorialCollectionResultInterface insertEditorialCollectionInDb(String issn, String name, String publisher) throws Exception;
}
