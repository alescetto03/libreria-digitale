package PostgresImplementationDAO;

import DAO.UserDAOInterface;
import DAO.UserResultInterface;

import java.sql.*;

/**
 * Classe per l'interfacciamente con il database per l'entita' utente.
 * Permette di loggare o registrare un utente.
 */
public class UserDAO implements UserDAOInterface {

    public UserResultInterface login(String username, byte[] password) {
        final String query = "SELECT username, email, nome, cognome, data_nascita, amministratore FROM Utente WHERE Utente.username = ? AND Utente.password = ?";
        try (
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, username);
            statement.setBytes(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()){
                return new UserResult(
                        username,
                        result.getString("email"),
                        result.getString("nome"),
                        result.getString("cognome"),
                        result.getDate("data_nascita").toLocalDate(),
                        result.getBoolean("amministratore")
                );
            }
        } catch (SQLException e){
            System.out.println("Errore:" + e.getMessage());
        }
        return null;
    }

    public UserResultInterface register(String username, String email, byte[] password, String name, String surname, Date birthdate) {
        final String query = "INSERT INTO Utente(username, email, password, nome, cognome, data_nascita) VALUES (?, ?, ?, ?, ?, ?)RETURNING *";
        try (
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setBytes(3, password);
            statement.setString(4, name);
            statement.setString(5, surname);
            statement.setDate(6, birthdate);

            statement.execute();
            ResultSet result = statement.getResultSet();
            result.next();

            return new UserResult(result);
        } catch (SQLException e) {
            System.out.println("Errore:" + e.getMessage());
            return null;
        }
    }
}
