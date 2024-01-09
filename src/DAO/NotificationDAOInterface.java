package DAO;


import java.util.ArrayList;

public interface NotificationDAOInterface {
    ArrayList<NotificationDAOResult> getUserNotification(String username);
}
