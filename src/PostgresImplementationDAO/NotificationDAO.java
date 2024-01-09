package PostgresImplementationDAO;

import DAO.NotificationDAOInterface;
import DAO.NotificationDAOResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationDAO implements NotificationDAOInterface {

    @Override
    public ArrayList<NotificationDAOResult> getUserNotification(String username) {
        final String query = "SELECT messaggio, data_ora FROM Notifica WHERE Notifica.destinatario = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            ArrayList<NotificationDAOResult> userNotificationList = new ArrayList<NotificationDAOResult>();
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
