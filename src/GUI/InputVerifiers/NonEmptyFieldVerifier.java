package GUI.InputVerifiers;

import javax.swing.*;

public class NonEmptyFieldVerifier extends InputVerifier {
    private String errorMessage = "Il campo \"%s\" non può essere vuoto";
    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        String text = textField.getText().trim(); // Rimuovi eventuali spazi vuoti all'inizio o alla fine del testo
        return !text.isEmpty(); // Verifica se il campo di testo è vuoto dopo aver rimosso gli spazi vuoti
    }

    public String getErrorMessage() { return errorMessage; }
}

