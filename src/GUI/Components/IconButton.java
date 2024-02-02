package GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IconButton extends JButton {
    private boolean mousePress;
    public IconButton(String path, int width, int height, int scaleAlgorithm){
        this.setText("");
        ImageIcon buttonIcon = new ImageIcon(getClass().getResource(path));
        Image scaledImage = buttonIcon.getImage().getScaledInstance(width, height, scaleAlgorithm);
        buttonIcon = new ImageIcon(scaledImage);
        this.setIcon(buttonIcon);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                mousePress = true;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                mousePress = false;
            }
        });
    }
}
