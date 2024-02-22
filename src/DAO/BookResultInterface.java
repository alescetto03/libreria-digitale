package DAO;

import java.io.InputStream;

/**
 * Interfaccia che rappresenta una tupla della tabella Libro del database
 */
public interface BookResultInterface {
    String getIsbn();
    String getTitle();
    String getPublisher();
    String getFruitionMode();
    int getPublicationYear();
    InputStream getCover();
    String getDescription();
    String getBookType();
    String getGenre();
    String getTarget();
    String getTopic();

}
