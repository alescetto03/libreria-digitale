package GUI;

import Controller.AppController;
import GUI.Components.BooksCrudTable;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
        confirmButton.addActionListener((ActionEvent e) -> {

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
