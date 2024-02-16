package GUI;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(String font, int style, int size, Color button_color) {
        this.setFont(new Font(font, style, size));
        this.setBackground(button_color);
    }
}
