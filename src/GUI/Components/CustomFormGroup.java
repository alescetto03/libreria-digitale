package GUI.Components;

import GUI.InputVerifiers.InputVerifier;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CustomFormGroup extends JPanel {
    private JComponent field;
    private JLabel label;
    private GUI.InputVerifiers.InputVerifier verifier;
    private JLabel errorLabel = new JLabel(" ");
    public CustomFormGroup (JLabel label, JComponent field, InputVerifier verifier) {
        this.field = field;
        this.label = label;
        this.verifier = verifier;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        if (label != null) {
            add(label);
        }
        add(field);
        if (verifier != null) {
            field.setInputVerifier(verifier);
            errorLabel.setFont(new Font(errorLabel.getFont().getName(), Font.PLAIN, 12));
            errorLabel.setForeground(Color.RED);
            add(errorLabel);
        }
    }
    public JComponent getField() {
        return field;
    }

    public void onVerifyInput() {
        if (!verifier.verify(field)) {
            Border customBorder = new LineBorder(Color.RED, 1);
            field.setBorder(customBorder);
            errorLabel.setText(String.format(verifier.getErrorMessage(), label.getText()));
        }
    }
}
