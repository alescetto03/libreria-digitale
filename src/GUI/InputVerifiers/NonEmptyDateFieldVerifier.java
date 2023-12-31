package GUI.InputVerifiers;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;

public class NonEmptyDateFieldVerifier extends InputVerifier {
    private String errorMessage = "Il campo \"%s\" non può essere vuoto";
    @Override
    public boolean verify(JComponent input) {
        JDateChooser dateField = (JDateChooser) input;
        return dateField == null; // Verifica se il campo di data è vuoto
    }

    public String getErrorMessage() { return errorMessage; }
}

