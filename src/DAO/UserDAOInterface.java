package DAO;

public interface UserDAOInterface {
    UserResultInterface login();
    void register(String username, String email, String password, String name, String surname);
}
