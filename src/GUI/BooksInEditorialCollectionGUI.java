package GUI;

import Controller.AppController;
import GUI.Components.EditorialCollectionsCrudTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class BooksInEditorialCollectionGUI extends AppView {
    JPanel contentPane = new JPanel();
    JPanel titlePanel = new JPanel();
    JLabel collectionTitle = new JLabel();
    public BooksInEditorialCollectionGUI(AppController appController, Map<String, Object> editorialCollection, ArrayList<Map<String, Object>> booksInEditorialCollection, ArrayList<Map<String, Object>> books, AppView parentView) {
        super(appController);

        JButton goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(new AdminPageGUI(appController, new EditorialCollectionsCrudTable(parentView, "Collane:", new String[]{"issn", "nome", "editore"}, appController.getRenderedEditorialCollections())));
        });

        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        collectionTitle.setText((String) editorialCollection.get("name"));
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
        for (Map<String, Object> book: books) {
            JPanel item = new JPanel();
            JLabel isbn = new JLabel((String) book.get("isbn") + " | ");
            JLabel title = new JLabel((String) book.get("title"));
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(booksInEditorialCollection.contains(book));
            checkBox.addActionListener((ActionEvent e) -> {
                appController.updateBooksFromEditorialCollection((String) book.get("isbn"), (String) editorialCollection.get("issn"), checkBox.isSelected());
            });
            item.setLayout(new FlowLayout(FlowLayout.LEFT));
            item.add(isbn);
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
