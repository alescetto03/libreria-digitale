package GUI;

import Controller.AppController;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class UpdateNovelBook extends AppView {
    private JPanel contentPane;
    private JButton goBackButton;
    private JButton confirmButton;
    private JTextField isbnField;
    private JTextField titleField;
    private JComboBox fruitionModeField;
    private JTextField publisherField;
    private JYearChooser publicationYearField;
    private JTextField genreField;
    private JTextArea descriptionField;
    private ArrayList<String> data;
    private JPanel topBar;
    private JLabel titleLabel;
    private JPanel bottomWrapper;
    private JScrollPane scrollPane;
    private JPanel form;
    private JButton coverButton;
    private JPanel novelPanel;
    public UpdateNovelBook(AppController appController, ArrayList<String> data, String bookToUpdate) {
        super(appController);
        this.data = data;
        isbnField.setText(data.get(0));
        titleField.setText(data.get(1));
        publisherField.setText(data.get(2));
        fruitionModeField.setSelectedItem(data.get(3));
        descriptionField.setText(data.get(6));
        genreField.setText(data.get(7));
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(new AdminPageGUI(appController, new BooksCrudTable(this, "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, appController.getRenderedBooks())));
        });
        confirmButton.addActionListener((ActionEvent e) -> {
            String isbn = isbnField.getText().trim();
            String title = titleField.getText().trim();
            String publisher = publisherField.getText().trim();
            String fruitionMode = String.valueOf(fruitionModeField.getSelectedItem());
            int publicationYear = publicationYearField.getValue();
            String description = descriptionField.getText().trim();
            String genre = genreField.getText().trim();
            try {
                Map<String, Object> renderedData = appController.updateBookFromDatabase(bookToUpdate, isbn, title, publisher, fruitionMode, publicationYear, description, genre, null, null, "romanzo");
                appController.switchView(new AdminPageGUI(appController, new BooksCrudTable(this, "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, appController.getRenderedBooks())));
                JOptionPane.showMessageDialog(contentPane, "Il libro " + renderedData.get("isbn") + " - " + renderedData.get("title") + " è stato modificato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(contentPane, ex.getMessage(), "Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        publicationYearField = new JYearChooser();
        publicationYearField.setValue(Integer.parseInt(data.get(4)));
    }
}
