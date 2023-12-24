package GUI;

import Controller.AppController;
import GUI.Components.CustomButton;
import GUI.Components.LinkButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends AppView {
    private JPanel contentPane = new JPanel();
    private JLabel usernameLabel = new JLabel("Username:");
    private JTextField usernameField = new JTextField(20);
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordField = new JPasswordField(20);
    private CustomButton loginButton = new CustomButton("Login");
    private LinkButton registerButton = new LinkButton("Registrati");

    public LoginGUI(AppController appController) {
        super(appController);
        setTitle("Login");
        setDimension(new Dimension(350, 450));

        contentPane.add(usernameLabel);
        contentPane.add(usernameField);
        contentPane.add(passwordLabel);
        contentPane.add(passwordField);
        contentPane.add(loginButton);
        contentPane.add(registerButton);

        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
        });

        registerButton.addActionListener((ActionEvent e) -> {
            getAppController().switchView(new RegisterGUI(getAppController()));
        });
    }

    @Override
    public JPanel getContentPane() { return contentPane; }
}