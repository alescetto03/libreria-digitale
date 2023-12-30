package GUI;

import Controller.AppController;
import GUI.Components.CustomButton;
import GUI.Components.LinkButton;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegisterGUI extends AppView {
    private JPanel contentPane = new JPanel();
    private JLabel usernameLabel = new JLabel("Username");
    private JTextField usernameField = new JTextField(20);
    private JLabel emailLabel = new JLabel("Email");
    private JTextField emailField = new JTextField(20);
    private JLabel passwordLabel = new JLabel("Password");
    private JPasswordField passwordField = new JPasswordField(20);
    private JLabel confirmPasswordLabel = new JLabel("Password");
    private JPasswordField confirmPasswordField = new JPasswordField(20);
    private JLabel nameLabel = new JLabel("Nome");
    private JTextField nameField = new JTextField(20);
    private JLabel surnameLabel = new JLabel("Cognome");
    private JTextField surnameField = new JTextField(20);
    private JLabel birthdateLabel = new JLabel("Data di nascita");
    //private JTextField birthdateField = new JTextField(10);
    private JDateChooser birthdateField = new JDateChooser();
    private LinkButton loginButton = new LinkButton("Login");
    private CustomButton registerButton = new CustomButton("Registrati");

    public RegisterGUI(AppController appController) {
        super(appController);
        setTitle("Registrati");

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        // Username
        contentPane.add(usernameLabel);
        contentPane.add(usernameField);
        // Email
        contentPane.add(emailLabel);
        contentPane.add(emailField);
        // Password
        contentPane.add(passwordLabel);
        contentPane.add(passwordField);
        // Confirm password
        contentPane.add(confirmPasswordLabel);
        contentPane.add(confirmPasswordField);
        // Nome
        contentPane.add(nameLabel);
        contentPane.add(nameField);
        // Cognome
        contentPane.add(surnameLabel);
        contentPane.add(surnameField);
        // Data di nascita
        contentPane.add(birthdateLabel);
        contentPane.add(birthdateField);

        contentPane.add(registerButton);
        contentPane.add(loginButton);

        registerButton.addActionListener((ActionEvent e) -> {
            String password = String.valueOf(passwordField.getPassword());
            String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
            if (password.equals(confirmPassword)) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String name = nameField.getText();
                String surname = surnameField.getText();
                    // Analizza la stringa per ottenere un oggetto java.util.Date
                    java.util.Date utilDate = birthdateField.getDate();

                getAppController().registerUser(username, email, password, name, surname, utilDate);
            } else {
                JOptionPane.showMessageDialog(this.getContentPane(), "Le password non coincidono", "Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginButton.addActionListener((ActionEvent e) -> {
            getAppController().switchView(new LoginGUI(getAppController()));
        });
    }

    @Override
    public JPanel getContentPane() { return contentPane; }
}
