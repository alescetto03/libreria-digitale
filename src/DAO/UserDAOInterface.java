package DAO;

import java.sql.Date;

public interface UserDAOInterface {
    void login(String username, byte[] password);
    void register(String username, String email, byte[] password, String name, String surname, Date birthdate);
}
