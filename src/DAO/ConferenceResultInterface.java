package DAO;

import java.sql.Date;

public interface ConferenceResultInterface {
    int getId();

    String getPlace();

    Date getStartDate();

    Date getEndDate();

    String getOrganizer();

    String getManager();
}
