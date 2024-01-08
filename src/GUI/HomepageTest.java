package GUI;

import Controller.AppController;
import GUI.Components.CrudTable;
import GUI.Components.PersonalCollectionsCrudTable;

import javax.swing.*;
import java.awt.*;

public class HomepageTest extends AppView{
    JPanel contentPane = new JPanel();
    public HomepageTest(AppController appController) {
        super(appController);
        int marginSize = 10;
        contentPane.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        JPanel topBar = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(topBar);
        topBar.setLayout(new BorderLayout());

        JButton logoutButton = new JButton("Logout");
        topBar.add(logoutButton, BorderLayout.LINE_START);

        JPanel searchBarWrapper = new JPanel();
        topBar.add(searchBarWrapper, BorderLayout.CENTER);
        JTextField searchBar = new JTextField(20);
        JButton searchButton = new JButton("Cerca");
        searchBarWrapper.add(searchBar);
        searchBarWrapper.add(searchButton);

        JPanel buttonsWrapper = new JPanel();
        topBar.add(buttonsWrapper, BorderLayout.LINE_END);
        JButton notifyButton = new JButton("Notifiche");
        JButton adminButton = new JButton("Admin");
        buttonsWrapper.add(notifyButton);
        buttonsWrapper.add(adminButton);

        CrudTable personalCollections = new PersonalCollectionsCrudTable("Le tue raccolte:");
        contentPane.add(personalCollections);

        CrudTable savedCollections = new PersonalCollectionsCrudTable("Le tue raccolte salvate:");
        contentPane.add(savedCollections);
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
