package GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

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

//    @Override
//    protected void paintComponent(Graphics grphcs) {
//        Graphics2D g2 = (Graphics2D) grphcs.create();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        int width = getWidth();
//        int height = getHeight();
//        int size = Math.min(width, height);
//        int x = (width - size) / 2;
//        int y = (height - size) / 2;
//        if (mousePress) {
//            g2.setColor(new Color(158, 158, 158));
//        } else {
//            g2.setColor(new Color(199, 199, 199));
//        }
//        g2.fill(new Ellipse2D.Double(x, y, size, size));
//        g2.dispose();
//        super.paintComponent(grphcs);
//    }
}
