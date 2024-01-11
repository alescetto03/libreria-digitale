package DAO;


import java.util.ArrayList;

public interface NotificationDAOInterface {
    ArrayList<NotificationResultInterface> getUserNotification(String username);
}
