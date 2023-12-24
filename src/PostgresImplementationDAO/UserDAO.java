package PostgresImplementationDAO;

import DAO.UserDAOInterface;
import DAO.UserResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserDAOInterface {

    public UserResultInterface login() {
        return null;
    }

    public void register(String username, String email, String password, String name, String surname) {
        final String query = "INSERT INTO Utente VALUES (?, ?, ?, ?, ?, ?)";
        try (
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, name);
            statement.setString(5, surname);
            statement.setString(6, "1990-05-15");
            connection.setAutoCommit(false);
            ResultSet result = statement.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Errore:" + e.getMessage());
        }
    }
}
