package GUI;

import Controller.AppController;
import GUI.Components.BooksCrudTable;
import GUI.Components.ScientificPublicationsCrudTable;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class UpdateEducationalBook extends AppView {
    private JPanel contentPane;
    private JPanel topBar;
    private JButton goBackButton;
    private JLabel titleLabel;
    private JPanel bottomWrapper;
    private JButton confirmButton;
    private JScrollPane scrollPane;
    private JPanel form;
    private JTextField isbnField;
    private JTextField titleField;
    private JComboBox fruitionModeField;
    private JTextField publisherField;
    private JYearChooser publicationYearField;
    private JButton coverButton;
    private JPanel educationalpanel;
    private JTextField targetField;
    private JTextField topicField;
    private JTextArea descriptionField;
    private ArrayList<String> data;

    public UpdateEducationalBook(AppController appController, ArrayList<String> data, String bookToUpdate) {
        super(appController);
        this.data = data;
        isbnField.setText(data.get(0));
        titleField.setText(data.get(1));
        publisherField.setText(data.get(2));
        fruitionModeField.setSelectedItem(data.get(3));
        descriptionField.setText(data.get(6));
        targetField.setText(data.get(8));
        topicField.setText(data.get(9));
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
            String target = targetField.getText().trim();
            String topic =  topicField.getText().trim();
            try {
                Map<String, Object> renderedData = appController.updateBookFromDatabase(bookToUpdate, isbn, title, publisher, fruitionMode, publicationYear, description, null, target, topic, "didattico");
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
