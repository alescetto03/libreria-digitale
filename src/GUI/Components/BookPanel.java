package GUI.Components;

import Model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BookPanel extends JPanel {
    //private Book book;

    public BookPanel(String title, BufferedImage cover) {
        //this.book = book;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Etichetta per la copertina del libro
        ImageIcon converIcon = new ImageIcon(getClass().getResource("/GUI/images/HP_ordine_fenice.jpg"));
        Image scaledImage = converIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        converIcon = new ImageIcon(scaledImage);
        JLabel coverLabel = new JLabel(converIcon);
        coverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(coverLabel);

        // Etichetta per il nome del libro
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

//    private void initUI() {
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//
//        // Etichetta per il nome del libro
//        JLabel titleLabel = new JLabel(book.getTitle());
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
//        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        add(titleLabel);
//
//        // Etichetta per la copertina del libro
//        JLabel coverLabel = new JLabel(new ImageIcon(book.getCover()));
//        coverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        add(coverLabel);
//
//        setBorder(BorderFactory.createLineBorder(Color.BLACK));
//    }
}
