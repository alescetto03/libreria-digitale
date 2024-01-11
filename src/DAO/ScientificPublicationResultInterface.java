package DAO;

import java.io.InputStream;

public interface ScientificPublicationResultInterface {
    String getDoi();
    String getTitle();
    String getFruition_mode();
    int getPublication_year();
    InputStream getCover();
    String getDescription();
    String getPublisher();
}
