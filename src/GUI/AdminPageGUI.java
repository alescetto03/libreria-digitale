package GUI;

import Controller.AppController;
import GUI.Components.*;
import Model.Journal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class AdminPageGUI extends AppView {
    private JPanel contentPane;
    private JButton booksButton;
    private JButton publicationsButton;
    private JButton authorsButton;
    private JButton shopsButton;
    private JButton editorialCollections;
    private JButton seriesButton;
    private JButton journalsButton;
    private JButton conferencesButton;
    private JButton presentationHallsButton;
    private JPanel buttonsWrapper;
    private JPanel tableWrapper;
    private JButton publicationsJournalsButton;
    private JButton booksEditorialCollectionsButton;
    private JButton publicationsConferencesButton;
    private JButton booksPresentationHallsButton;
    private JButton booksShopsButton;
    private JPanel currentPanel;
    private CrudTable currentTable;
    private ArrayList<Map<String, Object>> currentData;
    public AdminPageGUI(AppController appController, ArrayList<Map<String, Object>> startData) {
        super(appController);
        currentData = startData;
        int marginSize = 10;
        contentPane.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        booksButton.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedBooks();
            currentTable = new BooksCrudTable(getAppController(), "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });
        publicationsButton.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedScienticPublications();
            currentTable = new ScientificPublicationsCrudTable(getAppController(), "Articoli scientifici:", new String[]{"doi", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });
        authorsButton.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedAuthors();
            currentTable = new AuthorsCrudTable(getAppController(), "Autori:", new String[]{"id", "nome", "data di nascita", "data di morte", "nazionalità", "biografia"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });
        shopsButton.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedStores();
            currentTable = new StoresCrudTable(getAppController(), "Negozi:", new String[]{"partita iva", "nome", "indirizzo", "url"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });
        editorialCollections.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedEditorialCollections();
            currentTable = new EditorialCollectionsCrudTable(getAppController(), "Collane:", new String[]{"issn", "nome", "editore"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });
        seriesButton.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedSeries();
            currentTable = new SeriesCrudTable(getAppController(), "Serie:", new String[]{"prequel", "sequel", "nome"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });
        journalsButton.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedJournals();
            currentTable = new JournalsCrudTable(getAppController(), "Riviste:", new String[]{"issn", "nome", "argomento", "anno di pubblicazione", "responsabile"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });
        conferencesButton.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedConferences();
            currentTable = new ConferencesCrudTable(getAppController(), "Conferenze:", new String[]{"id", "luogo", "data di inizio", "data di fine", "organizzatore", "responsabile"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });
        presentationHallsButton.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedPresentationHall();
            currentTable = new PresentationHallsCrudTable(getAppController(), "Librerie:", new String[]{"id", "nome", "indirizzo"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });

        publicationsJournalsButton.addActionListener((ActionEvent e) -> {
            appController.getScientificPublicationsFromJournals();
        });
        booksEditorialCollectionsButton.addActionListener((ActionEvent e) -> {

        });
        publicationsConferencesButton.addActionListener((ActionEvent e) -> {

        });
        booksPresentationHallsButton.addActionListener((ActionEvent e) -> {

        });
        booksShopsButton.addActionListener((ActionEvent e) -> {

        });
    }
    public void createUIComponents() {
        currentTable = new BooksCrudTable(getAppController(), "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, currentData);
        //button1 = new IconButton("/GUI/images/logout.png",30, 30, Image.SCALE_SMOOTH);
    }
    private void switchPanel() {

    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}