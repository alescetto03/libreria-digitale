package GUI;

import Controller.AppController;
import GUI.Components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class AdminPage extends AppView {
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
    ArrayList<Map<String, Object>> personalCollections;
    ArrayList<Map<String, Object>> savedCollections;
    private CrudTable currentTable;
    private ArrayList<Map<String, Object>> currentData;
    public AdminPage(AppController appController, ArrayList<Map<String, Object>> startData) {
        super(appController);
        this.personalCollections = personalCollections;
        this.savedCollections = savedCollections;
        currentData = startData;
        System.out.println(currentData);
        int marginSize = 10;
        contentPane.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        booksButton.addActionListener((ActionEvent e) -> {
            tableWrapper.remove(currentTable);
            currentData = appController.getRenderedBooks();
            currentTable = new BookCrudTable(getAppController(), "Le tue raccolte:", new String[]{"id", "nome", "visibilita"}, currentData);
            tableWrapper.add(currentTable, BorderLayout.CENTER);
            tableWrapper.validate();
            tableWrapper.repaint();
        });
        publicationsButton.addActionListener((ActionEvent e) -> {
            //tableWrapper.remove(currentTable);
            //currentTable = new PersonalCollectionsCrudTable(getAppController(), "Le tue raccolte salvate:", new String[]{"id", "nome", "visibilita"}, savedCollections);
            //tableWrapper.add(currentTable, BorderLayout.CENTER);
            //tableWrapper.validate();
            //tableWrapper.repaint();
        });
    }
    public void createUIComponents() {
        currentTable = new BookCrudTable(getAppController(), "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, currentData);
        //button1 = new IconButton("/GUI/images/logout.png",30, 30, Image.SCALE_SMOOTH);
    }
    private void switchPanel() {

    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
