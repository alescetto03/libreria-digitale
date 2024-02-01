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

    public File bookCover;

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

                    //Se l'immagine è stata caricata correttamente, estraiamo il nome e l'estensione
                    //e li inseriamo negli appositi campi.


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
            String fullName = bookCover.getName();
            int index = fullName.lastIndexOf('.');
            String extensionFile = fullName.substring(index + 1);
            try {
                ImageIO.write(ImageIO.read(bookCover), extensionFile, stream);
                byte[] data = stream.toByteArray();
                if (educationalRadioButton.isSelected()) {
                    appController.insertBookIntoDatabase(isbn, title, publisher, fruitionMode, publicationYear, data, description, null, "didattico", target, topic);
                } else {
                    appController.insertBookIntoDatabase(isbn, title, publisher, fruitionMode, publicationYear, data, description, genre, "romanzo", null, null);
                }
                appController.switchView(new AdminPageGUI(appController, new BooksCrudTable(this, "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, appController.getRenderedBooks())));
                JOptionPane.showMessageDialog(appController.getCurrentView().getContentPane(), "Inserimento effettuato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(appController.getCurrentView().getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
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
