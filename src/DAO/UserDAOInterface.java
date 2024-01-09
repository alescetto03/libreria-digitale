package DAO;

import java.sql.Date;

public interface UserDAOInterface {
    UserResultInterface login(String username, byte[] password);
    UserResultInterface register(String username, String email, byte[] password, String name, String surname, Date birthdate);
}
