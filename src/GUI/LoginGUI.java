package GUI;

import Controller.AppController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginGUI extends AppView{
    public JPanel contentPane;
    private JLabel title;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel registerLabel;

    public LoginGUI(AppController appController) {
        super(appController);

        //Impostazioni per il contentPane
        setTitle("Login");
        setDimension(new Dimension(350, 450));

        //Impostazioni per i vari font/dimensioni testo Label
        title.setFont(new Font("Arial", Font.BOLD, 20));
        usernameLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        passwordLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        registerLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        //Impostazioni per i vari font/dimensioni testo Button
        loginButton.setFont(new Font("Arial", Font.BOLD, 13));
        registerButton.setFont(new Font("Arial", Font.BOLD, 13));
        loginButton.setBackground(Color.darkGray);
        registerButton.setBackground(Color.darkGray);



        //Listener LoginButton
        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if(getAppController().authenticateUser(username, password)){
                getAppController().switchView(new HomePage(appController));
            } else {
                JOptionPane.showMessageDialog(contentPane, "Non è stato possibile effettuare il login. Riprovare.");
            }
        });

        //Listener RegisterButton
        registerButton.addActionListener((ActionEvent e) -> {
            getAppController().switchView(new RegisterGUI(appController));
        });
    }




    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
