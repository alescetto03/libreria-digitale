package GUI.InputVerifiers;

import javax.swing.*;

public class ConfirmPasswordVerifier extends InputVerifier {
    private String errorMessage = "La password e la conferma non coincidono";
    private JPasswordField passwordField;

    public ConfirmPasswordVerifier(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }
    @Override
    public boolean verify(JComponent input) {
        JPasswordField confirmPasswordField = (JPasswordField) input;
        String password = String.valueOf(confirmPasswordField.getPassword());
        String confirmPassword = String.valueOf(passwordField.getPassword());
        return password.equals(confirmPassword);
    }

    public String getErrorMessage() { return errorMessage; }
}
