package GUI;

import Controller.AppController;
import GUI.Components.IconButton;


import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomePage extends AppView{
    // Creazione del pannello principale
    private JPanel contentPane = new JPanel();
    // Creazione della barra di ricerca
    private JTextField searchBar = new JTextField(20);
    private IconButton searchButton = new IconButton("/GUI/images/search_icon.png",30, 30, Image.SCALE_SMOOTH);
    private IconButton notificationButton = new IconButton("/GUI/images/notification_icon.png",30, 30, Image.SCALE_SMOOTH);
    private IconButton accountButton = new IconButton("/GUI/images/account_icon.png",30, 30, Image.SCALE_SMOOTH);
    private JPanel personalCollectionsPanel = new JPanel();
    private JLabel personalCollectionsLabel = new JLabel("Le Tue Raccolte: ");
    private JScrollPane personalCollectionsSrollPane;
    private JPanel topPanel = new JPanel();


    public HomePage (AppController appController){
        super(appController);

        //Impostazioni della finestra
        setTitle("Home Page");
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setDimension(new Dimension(600, 600));

        topPanel.setSize(200, 30);
        //topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        // Imposta la dimensione preferita
        searchBar.setPreferredSize(new Dimension(200, 30));
        // Imposta il bordo rotondo
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        topPanel.add(searchBar);
        topPanel.add(searchButton);
        topPanel.add(Box.createHorizontalStrut(8)); //Distanziamento di N pixel tra i due bottoni
        topPanel.add(notificationButton);
        topPanel.add(Box.createHorizontalStrut(8));
        topPanel.add(accountButton);
        contentPane.add(topPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20))); // Spazio tra i pannelli

        personalCollectionsPanel.setLayout(new BorderLayout());
        personalCollectionsPanel.add(personalCollectionsLabel, BorderLayout.NORTH);
        DefaultListModel<String> raccolteListModel = new DefaultListModel<>();
        raccolteListModel.addElement("Raccolta 1");
        raccolteListModel.addElement("Raccolta 2");
        raccolteListModel.addElement("Raccolta 3");

        appController.getCollections();

        JList<String> raccolteList = new JList<>(raccolteListModel);

        personalCollectionsSrollPane = new JScrollPane(raccolteList);
        personalCollectionsSrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        personalCollectionsSrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        personalCollectionsSrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Imposta l'altezza massima della barra di scorrimento

        personalCollectionsPanel.add(personalCollectionsSrollPane, BorderLayout.CENTER);

        personalCollectionsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        personalCollectionsPanel.setPreferredSize(new Dimension(300, 150));


        contentPane.add(personalCollectionsPanel, BorderLayout.CENTER);
        notificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNotificationPopup(notificationButton);
            }
        });

    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
    private void showNotificationPopup(Component component) {
        JPopupMenu popupMenu = new JPopupMenu();
        // Aggiungi elementi al popupMenu per visualizzare le notifiche

        // Esempio: Aggiungi un'etichetta di notifica
        popupMenu.add(new JLabel("Notifica 1"));
        popupMenu.add(new JLabel("Notifica 2"));

        // Mostra il popupMenu
        popupMenu.show(component, 0, component.getHeight());
    }
//    private List<String> getRivisteUtenteDalDatabase(){
//
//    }
}


