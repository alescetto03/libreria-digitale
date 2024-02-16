package GUI;

import Controller.AppController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class ScientificPublicationsInJournalGUI extends AppView {
    JPanel contentPane = new JPanel();
    JPanel titlePanel = new JPanel();
    JLabel journalTitle = new JLabel();
    public ScientificPublicationsInJournalGUI(AppController appController, Map<String, Object> journal, ArrayList<Map<String, Object>> scientificPublicationsInJournal, ArrayList<Map<String, Object>> scientificPublications, AppView parentView) {
        super(appController);

        JButton goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(new AdminPageGUI(appController, new JournalsCrudTable(parentView, "Riviste:", new String[]{"issn", "nome", "argomento", "anno di pubblicazione", "responsabile"}, appController.getRenderedJournals())));
        });

        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        journalTitle.setText((String) journal.get("name"));
        journalTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(goBackButton);
        titlePanel.add(journalTitle);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(800, 500));

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
            item.setLayout(new FlowLayout(FlowLayout.LEFT));
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
