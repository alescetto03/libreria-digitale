package DAO;

import java.util.ArrayList;

public interface BookDAOInterface {
    ArrayList<BookResultInterface> getResearchedBook(String searchedBook);
    ArrayList<String> getResearchedSeries(String searchedSeries);
    ArrayList<BookResultInterface> getBooksFromCollection(int collectionId);
}
