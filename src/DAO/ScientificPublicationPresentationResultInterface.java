package DAO;

import java.sql.Date;

/**
 * Interfaccia che rappresenta una tupla della tabella Presentazione Articolo del database
 */
public interface ScientificPublicationPresentationResultInterface {
    ScientificPublicationResultInterface getPresentedScientificPublication();
    ConferenceResultInterface getConference();
    Date getPresentationDate();
}
