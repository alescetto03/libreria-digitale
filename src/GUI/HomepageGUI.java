package GUI;

import Controller.AppController;
import GUI.Components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class HomepageGUI extends AppView{
    JPanel contentPane = new JPanel();
    public HomepageGUI(AppController appController) {
        super(appController);
        int marginSize = 10;
        contentPane.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        JPanel topBar = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(topBar);
        topBar.setLayout(new BorderLayout());

        IconButton logoutButton = new IconButton("/GUI/images/logout.png",30, 30, Image.SCALE_SMOOTH);
        topBar.add(logoutButton, BorderLayout.LINE_START);

        JPanel searchBarWrapper = new JPanel();
        topBar.add(searchBarWrapper, BorderLayout.CENTER);
        JTextField searchBar = new JTextField(20);
        IconButton searchButton = new IconButton("/GUI/images/search_icon.png",30, 30, Image.SCALE_SMOOTH);
        searchBarWrapper.add(searchBar);
        searchBarWrapper.add(searchButton);

        JPanel buttonsWrapper = new JPanel();
        topBar.add(buttonsWrapper, BorderLayout.LINE_END);
        IconButton notifyButton = new IconButton("/GUI/images/notification_icon.png",30, 30, Image.SCALE_SMOOTH);
        IconButton adminButton = new IconButton("/GUI/images/account_icon.png",30, 30, Image.SCALE_SMOOTH);
        buttonsWrapper.add(notifyButton);
        buttonsWrapper.add(adminButton);

        notifyButton.addActionListener((ActionEvent e) -> {
            JPopupMenu popupMenu = new JPopupMenu();
            // Aggiungi elementi al popupMenu per visualizzare le notifiche

            for(Map<String, Object> item : appController.getRenderedNotifications()){
                popupMenu.add(new JLabel(String.valueOf(item.get("text"))));
            }
            // Mostra il popupMenu
            popupMenu.show(notifyButton, 0, notifyButton.getHeight());
        });


        searchButton.addActionListener((ActionEvent e) -> {
            String searchText = searchBar.getText();
            getAppController().showSearchResults(searchText);
        });

        logoutButton.addActionListener((ActionEvent e) -> {
            appController.logoutUser();
        });

        adminButton.addActionListener((ActionEvent e) -> {
            appController.switchView(new AdminPageGUI(appController, new BooksCrudTable(appController.getCurrentView(), "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, appController.getRenderedBooks())));
        });

        CrudTable personalCollectionsTable = new PersonalCollectionsCrudTable(this, "Le tue raccolte:", new String[]{"id", "nome", "visibilita"}, appController.getRenderedPersonalCollections());
        contentPane.add(personalCollectionsTable);

        CrudTable savedCollectionsTable = new SavedCollectionCrudTable(this, "Le tue raccolte salvate:", new String[]{"id", "nome", "proprietario"}, appController.getRenderedSavedCollections());
        contentPane.add(savedCollectionsTable);
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
