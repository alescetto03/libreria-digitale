package GUI;

import Controller.AppController;
import GUI.Components.CustomButton;
import GUI.Components.LinkButton;
import GUI.InputVerifiers.NonEmptyFieldVerifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class LoginGUI extends AppView {
    private JPanel contentPane = new JPanel();
    private JLabel usernameLabel = new JLabel("Username:");
    private JTextField usernameField = new JTextField(20);
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordField = new JPasswordField(20);
    private CustomButton loginButton = new CustomButton("Login");
    private LinkButton registerButton = new LinkButton("Registrati");
    private NonEmptyFieldVerifier inputVerifier = new NonEmptyFieldVerifier();

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

        // Aggiungo delle validation per gli input
        usernameField.setInputVerifier(inputVerifier);
        passwordField.setInputVerifier(inputVerifier);

        loginButton.addActionListener((ActionEvent e) -> {
            boolean isUsernameValid = usernameField.getInputVerifier().verify(usernameField);
            boolean isPasswordValid = passwordField.getInputVerifier().verify(passwordField);

            if (isUsernameValid && isPasswordValid) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                getAppController().authenticateUser(username, password);
            } else {
                JOptionPane.showMessageDialog(contentPane, "I campi username e password non possono essere vuoti!", "Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener((ActionEvent e) -> {
            getAppController().switchView(new RegisterGUI(getAppController()));
        });
    }

    @Override
    public JPanel getContentPane() { return contentPane; }
}