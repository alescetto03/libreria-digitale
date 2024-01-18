package GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public abstract class HorizontalScrollpanePanel extends JPanel {
    public abstract boolean onButtonPress();
    public HorizontalScrollpanePanel(String title, BufferedImage image) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        /** TODO::TROVARE IL MODO PER IMPLEMENTARE LE COPERTINE IN MANIERA DINAMICA **/
        ImageIcon coverIcon = new ImageIcon(getClass().getResource("/GUI/images/HP_ordine_fenice.jpg"));
        /** ENDTODO:: **/
        Image scaledImage = coverIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        coverIcon = new ImageIcon(scaledImage);
        JLabel coverLabel = new JLabel(coverIcon);
        coverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        coverLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        add(coverLabel);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        add(titleLabel);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    public HorizontalScrollpanePanel(String title, BufferedImage image, String buttonText) {
        this(title, image);
        JButton actionButton = new JButton(buttonText);
        actionButton.addActionListener((ActionEvent e) -> {
            onButtonPress();
        });
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(actionButton);
    }
}
