package GUI;

import Controller.AppController;
import GUI.Components.CustomButton;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegisterGUI extends AppView{
    public JPanel contentPane;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField nameField;
    private JTextField surnameField;
    private JDateChooser birthdateField;
    private CustomButton registerButton;
    private CustomButton loginButton;
    private JLabel title;
    private JLabel subtitle;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel birthdateLabel;

    public RegisterGUI(AppController appController) {
        super(appController);

        //Impostazioni contentPane
        setTitle("Registrati");
        setDimension(new Dimension(450, 500));


        //Impostazioni per i vari font/dimensioni testo Label
        title.setFont(new Font("Arial", Font.BOLD, 20));
        subtitle.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        usernameLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        emailLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        passwordLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        nameLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        surnameLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        birthdateLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));


        //Listener RegisterButton
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

                if (getAppController().registerUser(username, email, password, name, surname, utilDate)) {
                    getAppController().showHomepage();
                } else {
                    JOptionPane.showMessageDialog(this.getContentPane(), "Oops, qualcosa è andato storto durante la registrazione, riprova!", "Errore!!!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this.getContentPane(), "Le password non coincidono", "Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Listener LoginButton
        loginButton.addActionListener((ActionEvent e) -> {
            getAppController().switchView(new LoginGUI(getAppController()));
        });
    }

    private void createUIComponents() {
        birthdateField = new JDateChooser();
        loginButton = new CustomButton("Arial", Font.BOLD, 13, Color.darkGray);
        registerButton = new CustomButton("Arial", Font.BOLD, 13, Color.darkGray);
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
