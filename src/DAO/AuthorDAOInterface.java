package DAO;

import java.util.ArrayList;

public interface AuthorDAOInterface {
    ArrayList<AuthorResultInterface> getAll();
    boolean deleteAuthorById(int id);
}
