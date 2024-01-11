package PostgresImplementationDAO;

import DAO.NotificationResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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

    public Timestamp getDate_time() {
        return date_time;
    }


}
