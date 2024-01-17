package GUI;


import Controller.AppController;
import GUI.Components.BookPanel;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

public class SearchResultsGUI extends AppView {

    JPanel contentPane = new JPanel();

    public SearchResultsGUI(AppController appController, ArrayList<Map<String, Object>> searchedBook, ArrayList<Map<String, Object>> searchedPublications) {
        super(appController);
        //JScrollPane mainScrollpane = new JScrollPane(contentPane);
        //mainScrollpane.setPreferredSize(new Dimension(400, 300));
        int marginSize = 10;
        contentPane.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        if (!searchedBook.isEmpty()) {
            JPanel searchedBooksContainer = new JPanel();
            searchedBooksContainer.setLayout(new BorderLayout());
            JLabel searchedBooksLabel = new JLabel("Libri:");
            searchedBooksLabel.setHorizontalAlignment(JLabel.LEFT);
            JPanel searchedBooksScrollpaneViewport = new JPanel();
            searchedBooksScrollpaneViewport.setLayout(new BoxLayout(searchedBooksScrollpaneViewport, BoxLayout.X_AXIS));
            for(Map<String, Object> book : searchedBook){
                searchedBooksScrollpaneViewport.add(new BookPanel((String) book.get("title"), (BufferedImage) book.get("cover"), "Salva in una raccolta"));
            }
            JScrollPane searchedBooksScrollPane = new JScrollPane(searchedBooksScrollpaneViewport);
            searchedBooksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            searchedBooksScrollPane.setPreferredSize(new Dimension(400, 250));
            searchedBooksContainer.add(searchedBooksLabel, BorderLayout.NORTH);
            searchedBooksContainer.add(searchedBooksScrollPane, BorderLayout.CENTER);
            contentPane.add(searchedBooksContainer);
            searchedBooksContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        }
        if (!searchedPublications.isEmpty()) {
            JPanel searchedPublicationsContainer = new JPanel();
            searchedPublicationsContainer.setLayout(new BorderLayout());
            JLabel searchedPublicationsLabel = new JLabel("Articoli scientifici:");
            searchedPublicationsLabel.setHorizontalAlignment(JLabel.LEFT);
            JPanel searchedPublicationsScrollpaneViewport = new JPanel();
            searchedPublicationsScrollpaneViewport.setLayout(new BoxLayout(searchedPublicationsScrollpaneViewport, BoxLayout.X_AXIS));
            for(Map<String, Object> book : searchedPublications){
                searchedPublicationsScrollpaneViewport.add(new BookPanel((String) book.get("title"), (BufferedImage) book.get("cover"), "Salva in una raccolta"));
            }
            JScrollPane searchedPublicationsScrollPane = new JScrollPane(searchedPublicationsScrollpaneViewport);
            searchedPublicationsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            searchedPublicationsScrollPane.setPreferredSize(new Dimension(400, 250));
            searchedPublicationsContainer.add(searchedPublicationsLabel, BorderLayout.NORTH);
            searchedPublicationsContainer.add(searchedPublicationsScrollPane, BorderLayout.CENTER);
            contentPane.add(searchedPublicationsContainer);
            searchedPublicationsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        }
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}

/**
 *             JPanel searchedContainer = new JPanel();
 *             searchedPublicationsContainer.setLayout(new BorderLayout());
 *             JLabel searchedLabel = new JLabel("Articoli scientifici:");
 *             searchedLabel.setHorizontalAlignment(JLabel.LEFT);
 *             JPanel searchedScrollpaneViewport = new JPanel();
 *             searchedScrollpaneViewport.setLayout(new BoxLayout(searchedScrollpaneViewport, BoxLayout.X_AXIS));
 *             for(Map<String, Object> book : searchedPublications){
 *                 searchedScrollpaneViewport.add(new BookPanel((String) book.get("title"), (BufferedImage) book.get("cover"), "Salva in una raccolta", "ciccio"));
 *             }
 *             JScrollPane searchedScrollPane = new JScrollPane(searchedScrollpaneViewport);
 *             searchedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
 *             searchedScrollPane.setPreferredSize(new Dimension(400, 250));
 *             searchedContainer.add(searchedLabel, BorderLayout.NORTH);
 *             searchedContainer.add(searchedScrollPane, BorderLayout.CENTER);
 *             contentPane.add(searchedContainer);
 */