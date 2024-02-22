package DAO;

import java.io.InputStream;

/**
 * Interfaccia che rappresenta una tupla della tabella Articolo Scientifico del database
 */
public interface ScientificPublicationResultInterface {
    String getDoi();
    String getTitle();
    String getFruitionMode();
    int getPublicationYear();
    InputStream getCover();
    String getDescription();
    String getPublisher();
}
