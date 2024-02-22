package DAO;

import java.sql.Date;

/**
 * Interfaccia che rappresenta una tupla della tabella Presentazione Libro del database
 */
public interface BookPresentationResultInterface {
    BookResultInterface getPresentedBook();
    PresentationHallResultInterface getPresentationHall();
    Date getPresentationDate();
}
