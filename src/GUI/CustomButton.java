package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Classe rappresentante un bottone con un font e un colore customizzato
 */
public class CustomButton extends JButton {
    public CustomButton(String font, int style, int size, Color button_color) {
        this.setFont(new Font(font, style, size));
        this.setBackground(button_color);
    }
}
