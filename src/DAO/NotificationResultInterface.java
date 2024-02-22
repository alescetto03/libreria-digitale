package DAO;

import java.sql.Timestamp;

/**
 * Interfaccia che rappresenta una tupla della tabella Notifica del database
 */
public interface NotificationResultInterface {
    String getText();
    Timestamp getDateTime();


}
