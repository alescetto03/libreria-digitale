package DAO;

import java.io.InputStream;

public interface BookResultInterface {
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
