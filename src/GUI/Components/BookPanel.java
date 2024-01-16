package GUI.Components;

import Model.Book;

import javax.swing.*;
import java.awt.*;

public class BookPanel extends JPanel {
    private Book book;

    public BookPanel(Book book) {
        this.book = book;
        initUI();
    }

    private void initUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Etichetta per il nome del libro
        JLabel titleLabel = new JLabel(book.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        // Etichetta per la copertina del libro
        JLabel coverLabel = new JLabel(new ImageIcon(book.getCover()));
        coverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(coverLabel);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}
