package Controller;

import DAO.UserDAOInterface;
import GUI.AppView;
import GUI.LoginGUI;
import PostgresImplementationDAO.UserDAO;

import javax.swing.*;

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

    public void authenticateUser() {

    }

    public void registerUser(String username, String email, String password, String name, String surname) {
        this.userDAO.register(username, email, password, name, surname);
    }
}
