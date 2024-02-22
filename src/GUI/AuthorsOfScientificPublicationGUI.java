package GUI;

import Controller.AppController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

/**
 * View che permette all'utente di gestire gli autori di un articolo scientifico
 */
public class AuthorsOfScientificPublicationGUI extends AppView {
    JPanel contentPane = new JPanel();
    JPanel titlePanel = new JPanel();
    JLabel scientificPublicationTitle = new JLabel();
    public AuthorsOfScientificPublicationGUI(AppController appController, Map<String, Object> scientificPublication, ArrayList<Map<String, Object>> authorsOfBook, ArrayList<Map<String, Object>> authors, AppView parentView) {
        super(appController);

        JButton goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(new AdminPageGUI(appController, new ScientificPublicationsCrudTable(parentView, "Articoli scientifici:", new String[]{"doi", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione"}, appController.getRenderedScientificPublications())));
        });

        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        scientificPublicationTitle.setText((String) scientificPublication.get("name"));
        scientificPublicationTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(goBackButton);
        titlePanel.add(scientificPublicationTitle);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(800, 500));

        JPanel itemsWrapper = new JPanel();
        itemsWrapper.setLayout(new BoxLayout(itemsWrapper, BoxLayout.Y_AXIS));
        itemsWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        ArrayList<JPanel> items = new ArrayList<>();
        for (Map<String, Object> author: authors) {
            JPanel item = new JPanel();
            JLabel doi = new JLabel( author.get("id") + " | ");
            JLabel title = new JLabel((String) author.get("name"));
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(authorsOfBook.contains(author));
            checkBox.addActionListener((ActionEvent e) -> {
                appController.updateAuthorsOfScientificPublication((int) author.get("id"), (String) scientificPublication.get("doi"), checkBox.isSelected());
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
