package DAO;

import java.sql.Date;

public interface PublicationConferenceResultInterface {
    String getDoi();

    int getId();

    String getPublicationTitle();

    String getPlace();

    Date getStartDate();

    Date getEndDate();
}
