package GUI.Components;

import javax.swing.*;
import java.awt.Image;

public class IconButton extends JButton {
    public IconButton(String path, int width, int height, int scaleAlgorithm){
        ImageIcon buttonIcon = new ImageIcon(getClass().getResource(path));
        Image scaledImage = buttonIcon.getImage().getScaledInstance(width, height, scaleAlgorithm);
        buttonIcon = new ImageIcon(scaledImage);
        this.setIcon(buttonIcon);
        this.setBorder(BorderFactory.createEmptyBorder());

    }
}
