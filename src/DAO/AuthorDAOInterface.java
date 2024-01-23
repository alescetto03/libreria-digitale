package DAO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public interface AuthorDAOInterface {
    ArrayList<AuthorResultInterface> getAll();
    boolean deleteAuthorById(int id);
    AuthorResultInterface updateAuthorById(int authorToUpdate, String name, LocalDate birthDate, LocalDate deathDate, String nationality, String biography) throws Exception;
}
