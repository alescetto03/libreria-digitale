package GUI.Components;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(String text) {
        setText(text);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
