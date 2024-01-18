package DAO;

import java.sql.Date;
import java.time.LocalDate;

public interface AuthorResultInterface {
    int getId();

    String getName();

    Date getBirthDate();

    Date getDeathDate();

    String getNationality();

    String getBio();
}
