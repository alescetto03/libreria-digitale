package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * Superclasse che rappresenta uno Scrollpane orizzontale contenente degli elementi
 */
public abstract class HorizontalScrollpanePanel extends JPanel {
    public abstract boolean onSaveButtonPress();
    public abstract void onViewButtonPress();
    public HorizontalScrollpanePanel(String title, BufferedImage image) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ImageIcon coverIcon;

        if (image == null) {
            coverIcon = new ImageIcon(getClass().getResource("/GUI/images/null_icon.png"));
        }
        else
            coverIcon = new ImageIcon(image);


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
            onSaveButtonPress();
        });
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(actionButton);
    }

    public HorizontalScrollpanePanel(String title, String firstButton){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel buttonPanel = new JPanel();

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        add(titleLabel);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JButton actionButton = new JButton(firstButton);
        actionButton.addActionListener((ActionEvent e) -> {
            if (!onSaveButtonPress()){
                JOptionPane.showMessageDialog(this, "La raccolta che stai cercando di salvare esiste gia' nelle tue raccolte.", "ATTENZIONE!!!", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Raccolta salvata con successo", "Successo!!!", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(actionButton);

        IconButton viewButton = new IconButton("/GUI/images/view.png", 18, 18, Image.SCALE_SMOOTH);
        viewButton.addActionListener((ActionEvent e) -> {
            onViewButtonPress();
        });
        viewButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonPanel.add(viewButton);

        add(buttonPanel);
    }

}
