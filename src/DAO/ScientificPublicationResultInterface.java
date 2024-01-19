package DAO;

import java.io.InputStream;

public interface ScientificPublicationResultInterface {
    String getDoi();
    String getTitle();
    String getFruitionMode();
    int getPublicationYear();
    InputStream getCover();
    String getDescription();
    String getPublisher();
}
