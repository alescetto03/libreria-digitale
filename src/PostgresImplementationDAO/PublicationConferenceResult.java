package PostgresImplementationDAO;

import DAO.PublicationConferenceResultInterface;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicationConferenceResult implements PublicationConferenceResultInterface {
    String doi;
    int id;
    String publication_title;
    String place;
    Date startDate;
    Date endDate;

    public PublicationConferenceResult(String doi, int id, String publication_title, String place, Date startDate, Date endDate) {
        this.doi = doi;
        this.id = id;
        this.publication_title = publication_title;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public PublicationConferenceResult(ResultSet resultSet) throws SQLException {
        this(
                resultSet.getString("doi"),
                resultSet.getInt("id"),
                resultSet.getString("titolo_articolo"),
                resultSet.getString("luogo"),
                resultSet.getDate("data_inizio"),
                resultSet.getDate("data_fine")
        );
    }

    public String getDoi() {
        return doi;
    }

    public int getId() {
        return id;
    }

    public String getPublicationTitle() {
        return publication_title;
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
}
