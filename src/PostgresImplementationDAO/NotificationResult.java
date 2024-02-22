package PostgresImplementationDAO;

import DAO.NotificationResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Notifica del database
 */
public class NotificationResult implements NotificationResultInterface {
    String text;
    Timestamp date_time;

    public NotificationResult(String text, Timestamp date_time) {
        this.text = text;
        this.date_time = date_time;
    }

    public NotificationResult(ResultSet result) throws SQLException {
        this(
                result.getString("messaggio"),
                result.getTimestamp("data_ora")
        );
    }


    public String getText() {
        return text;
    }

    public Timestamp getDateTime() {
        return date_time;
    }
}
