package DAO;

import java.util.ArrayList;

public interface BookDAOInterface {
    ArrayList<BookDAOResult> getResearchedBook(String searchedBook);
}
