package PostgresImplementationDAO;

import DAO.ConferenceResultInterface;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Conferenza del database
 */
public class ConferenceResult implements ConferenceResultInterface {
    private int id;
    private String place;
    private Date startDate;
    private Date endDate;
    private String organizer;
    private String manager;

    public ConferenceResult(int id, String place, Date startDate, Date endDate, String organizer, String manager) {
        this.id = id;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
        this.manager = manager;
    }

    public ConferenceResult(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getInt("cod_conferenza"),
            resultSet.getString("luogo"),
            resultSet.getDate("data_inizio"),
            resultSet.getDate("data_fine"),
            resultSet.getString("organizzatore"),
            resultSet.getString("responsabile")
        );
    }

    public int getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getManager() {
        return manager;
    }
}
