package DAO;

import java.sql.Date;

/**
 * Interfaccia che rappresenta una tupla della tabella Conferenza del database
 */
public interface ConferenceResultInterface {
    int getId();

    String getPlace();

    Date getStartDate();

    Date getEndDate();

    String getOrganizer();

    String getManager();
}
