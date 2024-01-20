package GUI;

import Controller.AppController;
import GUI.Components.BooksInCollectionCrudTable;
import GUI.Components.CrudTable;
import GUI.Components.PublicationsInCollectionCrudTable;
import GUI.Components.ScientificPublicationsCrudTable;
import Model.ScientificPublication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class ScientificPublicationsInJournalGUI extends AppView {
    JPanel contentPane = new JPanel();
    JPanel titlePanel = new JPanel();
    JLabel collectionTitle = new JLabel();
    public ScientificPublicationsInJournalGUI(AppController appController, Map<String, Object> journal, ArrayList<Map<String, Object>> scientificPublicationsInJournal, ArrayList<Map<String, Object>> scientificPublications) {
        super(appController);

        JButton goBackButton = new JButton("Torna indietro");

        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        collectionTitle.setText((String) journal.get("name"));
        collectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(goBackButton);
        titlePanel.add(collectionTitle);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(600, 300));

        JPanel itemsWrapper = new JPanel();
        itemsWrapper.setLayout(new BoxLayout(itemsWrapper, BoxLayout.Y_AXIS));
        itemsWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        ArrayList<JPanel> items = new ArrayList<>();
        for (Map<String, Object> scientificPublication: scientificPublications) {
            JPanel item = new JPanel();
            JLabel doi = new JLabel((String) scientificPublication.get("doi") + " | ");
            JLabel title = new JLabel((String) scientificPublication.get("title"));
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(scientificPublicationsInJournal.contains(scientificPublication));
            checkBox.addActionListener((ActionEvent e) -> {
                appController.updateScientificPublicationsFromJournal((String) scientificPublication.get("doi"), (String) journal.get("issn"), checkBox.isSelected());
            });
            item.setLayout(new FlowLayout());
            item.add(doi);
            item.add(title);
            item.add(checkBox);
            items.add(item);
            itemsWrapper.add(item);
        }
        contentPane.add(titlePanel, BorderLayout.PAGE_START);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(itemsWrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
