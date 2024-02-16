package GUI;

import Controller.AppController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        ArrayList<Map<String, Object>> personalCollection = appController.getRenderedPersonalCollections();

        JButton goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(previousView);
        });
        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        goBackButton.setLayout(new GridLayout());

        title.setText("Raccolte personali di " + appController.getLoggedUsername());
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(subTitle);
        //subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(goBackButton,  BorderLayout.LINE_START);
        titlePanel.add(title,  BorderLayout.CENTER);
        //titlePanel.add(subTitle, BorderLayout.PAGE_END);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.setAlignmentX(Component.TOP_ALIGNMENT);
        titlePanel.setPreferredSize(new Dimension(350, 75));

        CustomCheckBoxPanel checkBox = new CustomCheckBoxPanel(appController, personalCollection, book_isbn, publication_doi, previousView);
        checkBoxPanel.add(checkBox);

        contentPane.setLayout(new BorderLayout());
        //contentPane.setPreferredSize(new Dimension(350, 500));

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(checkBoxPanel, BorderLayout.CENTER);
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
