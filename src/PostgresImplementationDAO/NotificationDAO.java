package PostgresImplementationDAO;

import DAO.NotificationDAOInterface;
import DAO.NotificationResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe per l'interfacciamente con il database per l'entita' notifica.
 * Permette di reperire le notifiche di un utente
 */

public class NotificationDAO implements NotificationDAOInterface {

    @Override
    public ArrayList<NotificationResultInterface> getUserNotification(String username) {
        final String query = "SELECT messaggio, data_ora FROM Notifica WHERE Notifica.destinatario = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            ArrayList<NotificationResultInterface> userNotificationList = new ArrayList<NotificationResultInterface>();
            while(result.next()){
                NotificationResult notification = new NotificationResult(result);
                userNotificationList.add(notification);
            }

            return userNotificationList;

        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }
}
