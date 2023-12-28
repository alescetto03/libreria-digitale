package Controller;

import DAO.UserDAOInterface;
import GUI.AppView;
import GUI.LoginGUI;
import PostgresImplementationDAO.UserDAO;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.sql.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppController {
    JFrame currentWindow;
    UserDAOInterface userDAO = new UserDAO();

    public static void main(String[] args) { (new AppController()).showLogin(); }

    public void showView(AppView view) {
        currentWindow = new JFrame(view.getTitle());
        currentWindow.add(view.getContentPane());

        if (view.getDimension() != null) {
            currentWindow.setSize(view.getDimension());
        } else {
            currentWindow.pack();
        }
        currentWindow.setLocationRelativeTo(null);
        currentWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentWindow.setVisible(true);
    }

    public void closeCurrentView() { currentWindow.dispose(); }

    public void switchView(AppView destinationView) {
        closeCurrentView();
        showView(destinationView);
    }

    public void showLogin() { showView(new LoginGUI(this)); }

    public void authenticateUser(String username, String password) {
        MessageDigest digest;
        try{
            digest = MessageDigest.getInstance("SHA-512");
        }catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return;
        }
        byte[] hashedPassword = digest.digest(password.getBytes());

        this.userDAO.login(username, hashedPassword);
    }

    public void registerUser(String username, String email, String password, String name, String surname, java.util.Date birthdate) {
        // Converti l'oggetto java.util.Date in java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(birthdate.getTime());
        this.userDAO.register(username, email, password.getBytes(StandardCharsets.UTF_8), name, surname, sqlDate);
    }
}
