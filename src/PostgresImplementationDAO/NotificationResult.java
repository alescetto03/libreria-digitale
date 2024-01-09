package PostgresImplementationDAO;

import DAO.NotificationDAOResult;
import Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class NotificationResult implements NotificationDAOResult {
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
