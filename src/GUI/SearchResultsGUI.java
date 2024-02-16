package GUI;


import Controller.AppController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class SearchResultsGUI extends AppView {

    JPanel contentPane = new JPanel();
    JPanel topPanel = new JPanel();
    JScrollPane contentScrollPane = new JScrollPane();

    public SearchResultsGUI(AppController appController, ArrayList<Map<String, Object>> searchedBook, ArrayList<Map<String, Object>> searchedPublications, ArrayList<Map<String, Object>> searchedCollections, Map<String, String> storeBySeries, AppView previousView) {
        super(appController);

        JButton goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(previousView);
        });
        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        topPanel.add(goBackButton);
        contentPane.add(topPanel);

        int marginSize = 10;
        contentPane.setPreferredSize(new Dimension(1800, 900));
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
                searchedBooksScrollpaneViewport.add(new BookPanel(appController, book, "Salva in una raccolta"));
            }
            JScrollPane searchedBooksScrollPane = new JScrollPane(searchedBooksScrollpaneViewport);
            searchedBooksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            searchedBooksScrollPane.setPreferredSize(new Dimension(400, 250));
            searchedBooksContainer.add(searchedBooksLabel, BorderLayout.NORTH);
            searchedBooksContainer.add(searchedBooksScrollPane, BorderLayout.CENTER);
            contentPane.add(searchedBooksContainer);
            searchedBooksContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        }
        else {
            JPanel searchedBooksContainer = new JPanel();
            searchedBooksContainer.setLayout(new BorderLayout());
            JLabel searchedBooksLabel = new JLabel("Libri:");
            searchedBooksLabel.setHorizontalAlignment(JLabel.LEFT);
            JLabel noBookFoud = new JLabel("Nessun libro trovato con questo nome.");
            noBookFoud.setHorizontalAlignment(JLabel.CENTER);
            JPanel searchedBooksScrollpaneViewport = new JPanel();
            //searchedBooksScrollpaneViewport.setLayout(new BoxLayout(searchedBooksScrollpaneViewport, BoxLayout.X_AXIS));
            searchedBooksScrollpaneViewport.add(noBookFoud);
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
            for(Map<String, Object> publication : searchedPublications){
                searchedPublicationsScrollpaneViewport.add(new PublicationPanel(appController, publication, "Salva in una raccolta"));
            }
            JScrollPane searchedPublicationsScrollPane = new JScrollPane(searchedPublicationsScrollpaneViewport);
            searchedPublicationsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            searchedPublicationsScrollPane.setPreferredSize(new Dimension(400, 250));
            searchedPublicationsContainer.add(searchedPublicationsLabel, BorderLayout.NORTH);
            searchedPublicationsContainer.add(searchedPublicationsScrollPane, BorderLayout.CENTER);
            contentPane.add(searchedPublicationsContainer);
            searchedPublicationsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        }
        else {
            JPanel searchedPublicationsContainer = new JPanel();
            searchedPublicationsContainer.setLayout(new BorderLayout());
            JLabel searchedPublicationsLabel = new JLabel("Articoli scientifici:");
            searchedPublicationsLabel.setHorizontalAlignment(JLabel.LEFT);
            JLabel noPublicationFound = new JLabel("Nessun articolo scientifico trovato con questo nome.");
            noPublicationFound.setHorizontalAlignment(JLabel.CENTER);
            JPanel searchedPublicationsScrollpaneViewport = new JPanel();
            //searchedPublicationsScrollpaneViewport.setLayout(new BoxLayout(searchedPublicationsScrollpaneViewport, BoxLayout.X_AXIS));
            searchedPublicationsScrollpaneViewport.add(noPublicationFound);
            JScrollPane searchedPublicationsScrollPane = new JScrollPane(searchedPublicationsScrollpaneViewport);
            searchedPublicationsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            searchedPublicationsScrollPane.setPreferredSize(new Dimension(400, 250));
            searchedPublicationsContainer.add(searchedPublicationsLabel, BorderLayout.NORTH);
            searchedPublicationsContainer.add(searchedPublicationsScrollPane, BorderLayout.CENTER);
            contentPane.add(searchedPublicationsContainer);
            searchedPublicationsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        }
        if (!searchedCollections.isEmpty()){
            JPanel searchedCollectionsContainer = new JPanel();
            searchedCollectionsContainer.setPreferredSize(new Dimension(950, 200));
            searchedCollectionsContainer.setLayout(new BorderLayout());
            JLabel searchedCollectionsLabel = new JLabel("Raccolte:");
            searchedCollectionsLabel.setHorizontalAlignment(JLabel.LEFT);
            JPanel searchedCollectionsScrollpaneViewport = new JPanel();
            searchedCollectionsScrollpaneViewport.setLayout(new BoxLayout(searchedCollectionsScrollpaneViewport, BoxLayout.X_AXIS));
            for(Map<String, Object> collection : searchedCollections){
                searchedCollectionsScrollpaneViewport.add(new CollectionPanel(appController, collection, "Salva nei preferiti"));
            }
            JScrollPane searchedCollectionsScrollPane = new JScrollPane(searchedCollectionsScrollpaneViewport);
            searchedCollectionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            searchedCollectionsScrollPane.setPreferredSize(new Dimension(400, 250));
            searchedCollectionsContainer.add(searchedCollectionsLabel, BorderLayout.NORTH);
            searchedCollectionsContainer.add(searchedCollectionsScrollPane, BorderLayout.CENTER);
            contentPane.add(searchedCollectionsContainer);
            searchedCollectionsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        }
        else {
            JPanel searchedCollectionsContainer = new JPanel();
            searchedCollectionsContainer.setPreferredSize(new Dimension(950, 200));
            searchedCollectionsContainer.setLayout(new BorderLayout());
            JLabel searchedCollectionsLabel = new JLabel("Raccolte:");
            searchedCollectionsLabel.setHorizontalAlignment(JLabel.LEFT);
            JLabel noCollectionFound = new JLabel("Nessuna raccolta trovata con questo nome.");
            noCollectionFound.setHorizontalAlignment(JLabel.CENTER);
            JPanel searchedCollectionsScrollpaneViewport = new JPanel();
            //searchedCollectionsScrollpaneViewport.setLayout(new BoxLayout(searchedCollectionsScrollpaneViewport, BoxLayout.X_AXIS));
            searchedCollectionsScrollpaneViewport.add(noCollectionFound);
            JScrollPane searchedCollectionsScrollPane = new JScrollPane(searchedCollectionsScrollpaneViewport);
            searchedCollectionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            searchedCollectionsScrollPane.setPreferredSize(new Dimension(400, 250));
            searchedCollectionsContainer.add(searchedCollectionsLabel, BorderLayout.NORTH);
            searchedCollectionsContainer.add(searchedCollectionsScrollPane, BorderLayout.CENTER);
            contentPane.add(searchedCollectionsContainer);
            searchedCollectionsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        }
        if (!storeBySeries.isEmpty()){
            JPanel searchedSeriesContainer = new JPanel();
            searchedSeriesContainer.setPreferredSize(new Dimension(950, 200));
            searchedSeriesContainer.setLayout(new BorderLayout());
            JLabel searchedSeriesLabel = new JLabel("Serie:");
            searchedSeriesLabel.setHorizontalAlignment(JLabel.LEFT);
            JPanel searchedSeriesScrollpaneViewport = new JPanel();
            searchedSeriesScrollpaneViewport.setLayout(new BoxLayout(searchedSeriesScrollpaneViewport, BoxLayout.X_AXIS));
            for(Map.Entry<String, String> series : storeBySeries.entrySet()){
                JPanel seriesPanel = new JPanel();
                seriesPanel.setLayout(new BoxLayout(seriesPanel, BoxLayout.Y_AXIS));

                JLabel seriesNameLabel = new JLabel(series.getKey());
                System.out.println("Serie: " + series.getKey());
                seriesNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                seriesNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                seriesNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
                seriesPanel.add(seriesNameLabel);

                JLabel storeNameLabel = new JLabel("Negozio: " + series.getValue());
                System.out.println(series.getValue());
                storeNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                storeNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                storeNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
                seriesPanel.add(storeNameLabel);

                seriesPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                searchedSeriesScrollpaneViewport.add(seriesPanel);
            }
            JScrollPane searchedSeriesScrollPane = new JScrollPane(searchedSeriesScrollpaneViewport);
            searchedSeriesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            searchedSeriesScrollPane.setPreferredSize(new Dimension(400, 250));
            searchedSeriesContainer.add(searchedSeriesLabel, BorderLayout.NORTH);
            searchedSeriesContainer.add(searchedSeriesScrollPane, BorderLayout.CENTER);
            contentPane.add(searchedSeriesContainer);
            searchedSeriesContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
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