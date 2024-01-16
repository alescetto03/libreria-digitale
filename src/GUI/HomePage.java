package GUI;

import Controller.AppController;
import GUI.Components.*;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class HomePage extends AppView{
    private JTextField searchBar;
    private IconButton searchButton;
    private IconButton notificationButton;
    private IconButton accountButton;
    private JLabel personalCollectionLabel;
    private JLabel savedCollectionLabel;
    private JPanel contentPane;
    private JTable table1;
    public HomePage(AppController appController, ArrayList<Map<String, Object>> notification) {
        super(appController);
        setTitle("Home Page");
        setDimension(new Dimension(350, 300));
        createTable();
        searchButton.setText("");
        notificationButton.setText("");
        accountButton.setText("");


        notificationButton.addActionListener((ActionEvent e) -> {
            JPopupMenu popupMenu = new JPopupMenu();
            // Aggiungi elementi al popupMenu per visualizzare le notifiche

            for(Map<String, Object> item : notification){
                popupMenu.add(new JLabel(String.valueOf(item.get("text"))));
            }
            // Mostra il popupMenu
            popupMenu.show(notificationButton, 0, notificationButton.getHeight());
        });


        searchButton.addActionListener((ActionEvent e) -> {
            String searchText = searchBar.getText();
            getAppController().showSearchResults(searchText);
        });
    }

    private void createUIComponents() {
        searchButton = new IconButton("/GUI/images/search_icon.png",30, 30, Image.SCALE_SMOOTH);
        notificationButton = new IconButton("/GUI/images/notification_icon.png",30, 30, Image.SCALE_SMOOTH);
        accountButton = new IconButton("/GUI/images/account_icon.png",30, 30, Image.SCALE_SMOOTH);
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    private void createTable() {
        Object[][] data = {
                {"raccolta 1", null},
                {"raccolta 2", null},
                {"raccolta 3", null},
                {"raccolta 4", null},
        };
        table1.setModel(new DefaultTableModel(data, new String[]{"nome", "azioni"}));
        table1.setRowHeight(40);
        table1.setSelectionBackground(new java.awt.Color(56, 138, 112));
        //ActionsPanel actionsPanel = new ActionsPanel(true, true, true);
        //table1.getColumn("azioni").setCellRenderer(new TableActionsPanelRenderer(actionsPanel));
        //table1.getColumn("azioni").setCellEditor(new TableActionsPanelEditor(actionsPanel));
    }
}
