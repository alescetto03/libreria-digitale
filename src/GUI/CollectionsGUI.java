package GUI;

import Controller.AppController;
import GUI.Components.BooksInCollectionCrudTable;
import GUI.Components.CrudTable;
import GUI.Components.PublicationsInCollectionCrudTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class CollectionsGUI extends AppView{
    JPanel contentPane = new JPanel();
    JPanel bookPanel = new JPanel();
    JLabel collectionTitle = new JLabel();

    public CollectionsGUI(AppController appController, Map<String, Object> collection_data, ArrayList<Map<String, Object>> bookFromCollection, ArrayList<Map<String, Object>> publicationFromCollection) {
        super(appController);

        collectionTitle.setText(collection_data.get("name").toString());
        contentPane.add(collectionTitle);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setPreferredSize(new Dimension(1000, 700));

        CrudTable booksFromCollectionTable = new BooksInCollectionCrudTable(appController, "Libri nella raccolta:", new String[]{"isbn", "titolo", "editore", "modalita fruizione", "anno", "descrizione", "tipo libro", "genere", "target", "argomento"}, bookFromCollection, (Integer)collection_data.get("id"), collection_data.get("owner").toString());
        contentPane.add(booksFromCollectionTable);

        CrudTable publicationFromCollectionTable = new PublicationsInCollectionCrudTable(appController, "Articoli Scientifici nella raccolta:", new String[]{"doi", "titolo", "editore", "modalita fruizione", "anno", "descrizione"}, publicationFromCollection, (Integer)collection_data.get("id"), collection_data.get("owner").toString());
        contentPane.add(publicationFromCollectionTable);

    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
