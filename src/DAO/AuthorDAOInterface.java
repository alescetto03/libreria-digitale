package DAO;

import java.util.ArrayList;
import java.util.Date;

public interface AuthorDAOInterface {
    ArrayList<AuthorResultInterface> getAll();
    boolean deleteAuthorById(int id);
    AuthorResultInterface insertAuthorInDb (String name, java.sql.Date birth_date, java.sql.Date death_date, String nationality, String bio) throws Exception;
}
