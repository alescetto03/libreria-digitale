package GUI;

import Controller.AppController;
import GUI.Components.CustomCheckBoxPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class SaveItemInCollectionGUI extends AppView{
    JPanel contentPane = new JPanel();
    JPanel titlePanel = new JPanel();
    JPanel checkBoxPanel = new JPanel();
    JLabel title = new JLabel();
    JLabel subTitle = new JLabel("Scegli dove vuoi salvare il libro: ");
    public SaveItemInCollectionGUI(AppController appController, String book_isbn, String publication_doi, AppView previousView) {
        super(appController);
        ArrayList<Map<String, Object>> personalCollection = appController.getUserPersonalCollectionsList();

        JButton goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(previousView);
        });
        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        goBackButton.setLayout(new GridLayout());

        title.setText("Raccolte personali di " + personalCollection.get(0).get("owner"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        //subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(goBackButton,  BorderLayout.LINE_START);
        titlePanel.add(title,  BorderLayout.CENTER);
        //titlePanel.add(subTitle, BorderLayout.PAGE_END);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.setPreferredSize(new Dimension(950, 200));
        titlePanel.setAlignmentX(Component.TOP_ALIGNMENT);


        CustomCheckBoxPanel checkBox = new CustomCheckBoxPanel(appController, personalCollection, book_isbn, publication_doi, previousView);
        checkBoxPanel.add(subTitle);
        checkBoxPanel.add(checkBox);
        checkBoxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        checkBoxPanel.setPreferredSize(new Dimension(950, 550));


        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(1000, 700));

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(checkBoxPanel, BorderLayout.CENTER);


    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
