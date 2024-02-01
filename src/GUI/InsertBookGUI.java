package GUI;

import Controller.AppController;
import GUI.Components.BooksCrudTable;
import com.toedter.calendar.JYearChooser;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class InsertBookGUI extends AppView {
    private JPanel contentPane;
    private JPanel topBar;
    private JButton goBackButton;
    private JLabel titleLabel;
    private JButton confirmButton;
    private JPanel bottomWrapper;
    private JScrollPane scrollPane;
    private JPanel form;
    private JTextField isbnField;
    private JTextField titleField;
    private JComboBox fruitionModeField;
    private JTextField publisherField;
    private JButton coverButton;
    private JYearChooser publicationYearField;
    private JRadioButton novelRadioButton;
    private JRadioButton educationalRadioButton;
    private JTextArea descriptionField;
    private JTextField genreField;
    private JTextField targetField;
    private JTextField topicField;
    private JPanel novelPanel;
    private JPanel educationalpanel;

    public File bookCover = null;

    private static JFileChooser fileChooser = new JFileChooser();

    static {
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tutte le immagini", "jpg", "jpeg", "png"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Immagine JPEG", "jpg", "jpeg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Immagine PNG", "png"));
    };

    public InsertBookGUI(AppController appController) {
        super(appController);
        educationalpanel.setVisible(false);
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        educationalRadioButton.addActionListener((ActionEvent e) -> {
            educationalpanel.setVisible(true);
            novelPanel.setVisible(false);
        });
        novelRadioButton.addActionListener((ActionEvent e) -> {
            educationalpanel.setVisible(false);
            novelPanel.setVisible(true);
        });
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(new AdminPageGUI(appController, new BooksCrudTable(this, "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, appController.getRenderedBooks())));
        });

        coverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int returnValue = fileChooser.showOpenDialog(contentPane);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    bookCover = fileChooser.getSelectedFile();
                }
            }
        });

        confirmButton.addActionListener((ActionEvent e) -> {
            String isbn = isbnField.getText().trim();
            String title = titleField.getText().trim();
            String publisher = publisherField.getText().trim();
            String fruitionMode = String.valueOf(fruitionModeField.getSelectedItem());
            int publicationYear = publicationYearField.getValue();
            String description = descriptionField.getText().trim();
            String target = targetField.getText().trim();
            String topic =  topicField.getText().trim();
            String genre = genreField.getText().trim();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            String extensionFile = null;
            if (bookCover != null) {
                String fullName = bookCover.getName();
                extensionFile = fullName.substring(fullName.lastIndexOf('.') + 1);
            }

            try {
                if(bookCover != null) {
                    ImageIO.write(ImageIO.read(bookCover), extensionFile, stream);
                    byte[] cover = stream.toByteArray();
                    if (educationalRadioButton.isSelected()) {
                        appController.insertBookIntoDatabase(isbn, title, publisher, fruitionMode, publicationYear, cover, description, null, "didattico", target, topic);
                    } else {
                        appController.insertBookIntoDatabase(isbn, title, publisher, fruitionMode, publicationYear, cover, description, genre, "romanzo", null, null);
                    }
                    appController.switchView(new AdminPageGUI(appController, new BooksCrudTable(this, "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, appController.getRenderedBooks())));
                    JOptionPane.showMessageDialog(appController.getCurrentView().getContentPane(), "Inserimento effettuato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    if (educationalRadioButton.isSelected()) {
                        appController.insertBookIntoDatabase(isbn, title, publisher, fruitionMode, publicationYear, null, description, null, "didattico", target, topic);
                    } else {
                        appController.insertBookIntoDatabase(isbn, title, publisher, fruitionMode, publicationYear, null, description, genre, "romanzo", null, null);
                    }
                    appController.switchView(new AdminPageGUI(appController, new BooksCrudTable(this, "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, appController.getRenderedBooks())));
                    JOptionPane.showMessageDialog(appController.getCurrentView().getContentPane(), "Inserimento effettuato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(appController.getCurrentView().getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
                System.out.println(Arrays.toString(exception.getStackTrace()));
            }
        });

    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        publicationYearField = new JYearChooser();
    }
}
