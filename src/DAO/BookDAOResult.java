package DAO;

import PostgresImplementationDAO.BookResult;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public interface BookDAOResult {
    String getIsbn();
    String getTitle();
    String getPublisher();
    String getFruition_mode();
    int getPublication_year();
    InputStream getCover();
    String getDescription();
    String getBook_type();
    String getGenre();
    String getTarget();
    String getTopic();

}
