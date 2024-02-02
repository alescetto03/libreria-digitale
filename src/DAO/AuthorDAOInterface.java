package DAO;

import java.util.ArrayList;

public interface AuthorDAOInterface {
    ArrayList<AuthorResultInterface> getAll();
    boolean deleteAuthorById(int id);
    AuthorResultInterface updateAuthorById(int authorToUpdate, String name, java.sql.Date birthDate, java.sql.Date deathDate, String nationality, String biography) throws Exception;
    AuthorResultInterface insertAuthorInDb (String name, java.sql.Date birth_date, java.sql.Date death_date, String nationality, String bio) throws Exception;
}
