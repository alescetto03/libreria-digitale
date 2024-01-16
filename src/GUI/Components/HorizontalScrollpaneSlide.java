package GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class HorizontalScrollpaneSlide extends JPanel {
    protected IconButton actionButton;
    public abstract boolean onButtonPress();
    public HorizontalScrollpaneSlide(String title, BufferedImage image, boolean displayActionButton) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        add(titleLabel);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}
