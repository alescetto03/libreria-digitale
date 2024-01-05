package DAO;

import java.time.LocalDate;
import java.util.Date;

public interface UserResultInterface {
    String getUsername();
    String getEmail();
    String getName();
    String getSurname();
    LocalDate getBirthdate();
    boolean isAdmin();
}
