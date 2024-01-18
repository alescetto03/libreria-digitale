package DAO;

import java.io.InputStream;

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
