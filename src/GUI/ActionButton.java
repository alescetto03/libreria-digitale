package GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ActionButton extends JButton {

    private boolean mousePress;

    public ActionButton() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        setFocusPainted(false);
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

    public ActionButton(String path, int width, int height, int scale_alg) {
        this();
        ImageIcon buttonIcon = new ImageIcon(getClass().getResource(path));
        Image scaledImage = buttonIcon.getImage().getScaledInstance(width, height, scale_alg);
        buttonIcon = new ImageIcon(scaledImage);
        setIcon(buttonIcon);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        if (mousePress) {
            g2.setColor(new Color(158, 158, 158));
        } else {
            g2.setColor(new Color(199, 199, 199));
        }
        g2.fill(new Ellipse2D.Double(x, y, size, size));
        g2.dispose();
        super.paintComponent(grphcs);
    }
}