package PostgresImplementationDAO;

import DAO.UserDAOInterface;
import DAO.UserResultInterface;

import java.sql.*;

public class UserDAO implements UserDAOInterface {

    public void login(String username, byte[] password) {
        final String query = "SELECT username, password, amministratore FROM Utente WHERE Utente.username = ? AND Utente.password = ?";
        try (
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, username);
            statement.setBytes(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()){
                System.out.println("Utente trovato.");
            } else System.out.println("Utente NON trovato.");

        } catch (SQLException e){
            System.out.println("Errore:" + e.getMessage());
        }
    }

    public void register(String username, String email, byte[] password, String name, String surname, Date birthdate) {
        final String query = "INSERT INTO Utente(username, email, password, nome, cognome, data_nascita) VALUES (?, ?, ?, ?, ?, ?)";
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

            // Usa executeUpdate invece di executeQuery per un'operazione di inserimento
            int affectedRows = statement.executeUpdate();

            // Gestisci il risultato, se necessario
            if (affectedRows > 0) {
                System.out.println("Inserimento effettuato con successo!");
            } else {
                System.out.println("Nessun record inserito.");
            }

            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Errore:" + e.getMessage());
        }
    }
}
