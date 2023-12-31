package GUI;

import Controller.AppController;
import GUI.Components.CustomButton;
import GUI.Components.CustomDatePicker;
import GUI.Components.CustomFormGroup;
import GUI.Components.LinkButton;
import GUI.InputVerifiers.ConfirmPasswordVerifier;
import GUI.InputVerifiers.NonEmptyDateFieldVerifier;
import GUI.InputVerifiers.NonEmptyFieldVerifier;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterGUI extends AppView {
    private JPanel contentPane = new JPanel();
    int columnsNumber = 25;
    private NonEmptyFieldVerifier nonEmptyFieldVerifier = new NonEmptyFieldVerifier();
    private NonEmptyDateFieldVerifier nonEmptyDateFieldVerifier = new NonEmptyDateFieldVerifier();
    private CustomFormGroup usernameFormGroup = new CustomFormGroup(new JLabel("Username"), new JTextField(columnsNumber), nonEmptyFieldVerifier);
    private CustomFormGroup emailFormGroup = new CustomFormGroup(new JLabel("Email"), new JTextField(columnsNumber), nonEmptyFieldVerifier);
    private CustomFormGroup passwordFormGroup = new CustomFormGroup(new JLabel("Password"), new JPasswordField(columnsNumber), nonEmptyFieldVerifier);
    private ConfirmPasswordVerifier confirmPasswordVerifier = new ConfirmPasswordVerifier((JPasswordField) passwordFormGroup.getField());
    private CustomFormGroup confirmPasswordFormGroup = new CustomFormGroup(new JLabel("Conferma password"), new JPasswordField(columnsNumber), confirmPasswordVerifier);
    private CustomFormGroup nameFormGroup = new CustomFormGroup(new JLabel("Nome"), new JTextField(columnsNumber), nonEmptyFieldVerifier);
    private CustomFormGroup surnameFormGroup = new CustomFormGroup(new JLabel("Cognome"), new JTextField(columnsNumber), nonEmptyFieldVerifier);
    private CustomFormGroup birthdateFormGroup = new CustomFormGroup(new JLabel("Data di nascita"), new CustomDatePicker(), nonEmptyDateFieldVerifier);
    private LinkButton loginButton = new LinkButton("Login");
    private CustomButton registerButton = new CustomButton("Registrati");
    ArrayList<CustomFormGroup> formGroups = new ArrayList<>(Arrays.asList(usernameFormGroup, emailFormGroup, passwordFormGroup, confirmPasswordFormGroup, nameFormGroup, surnameFormGroup, birthdateFormGroup));
    public RegisterGUI(AppController appController) {
        super(appController);
        setTitle("Registrati");
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Form groups
        addCustomFormGroupWithMargin(usernameFormGroup);
        addCustomFormGroupWithMargin(emailFormGroup);
        addCustomFormGroupWithMargin(passwordFormGroup);
        addCustomFormGroupWithMargin(confirmPasswordFormGroup);
        addCustomFormGroupWithMargin(nameFormGroup);
        addCustomFormGroupWithMargin(surnameFormGroup);
        addCustomFormGroupWithMargin(birthdateFormGroup);
        // Pulsanti di register e login
        contentPane.add(registerButton);
        contentPane.add(loginButton);

        registerButton.addActionListener((ActionEvent e) -> {
            formGroups.forEach(CustomFormGroup::onVerifyInput);
            JFrame frame = (JFrame) SwingUtilities.getRootPane(contentPane).getTopLevelAncestor();
            System.out.println("Larghezza" + contentPane.getWidth());
            System.out.println("Altezza" + contentPane.getHeight());
            String username = ((JTextField) usernameFormGroup.getField()).getText();
            String email = ((JTextField) emailFormGroup.getField()).getText();
            String password = String.valueOf(((JPasswordField) passwordFormGroup.getField()).getPassword());
            String confirmPassword = String.valueOf(((JPasswordField) confirmPasswordFormGroup.getField()).getPassword());
            String name = ((JTextField) nameFormGroup.getField()).getText();
            String surname = ((JTextField) surnameFormGroup.getField()).getText();
            java.util.Date utilDate = ((JDateChooser) birthdateFormGroup.getField()).getDate();
            try {
                getAppController().registerUser(username, email, password, confirmPassword, name, surname, utilDate);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this.getContentPane(), exception.getMessage(), "Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginButton.addActionListener((ActionEvent e) -> {
            getAppController().switchView(new LoginGUI(getAppController()));
        });
    }

    private void addCustomFormGroupWithMargin(CustomFormGroup formGroup) {
        contentPane.add(formGroup);
        // Aggiungo un bordo inferiore vuoto per creare il margine tra i form group
        formGroup.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    }

    @Override
    public JPanel getContentPane() { return contentPane; }
}
