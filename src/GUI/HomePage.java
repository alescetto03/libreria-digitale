package GUI;

import Controller.AppController;
import GUI.Components.ActionButton;
import GUI.Components.TableActionsPanelRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HomePage extends AppView{
    private JTextField searchBar;
    private ActionButton searchButton;
    private JButton notificationButton;
    private JButton accountButton;
    private JLabel personalCollectionLabel;
    private JLabel savedCollectionLabel;
    private JPanel contentPane;
    private JTable table1;
    public HomePage(AppController appController) {
        super(appController);
        setTitle("Home Page");
        setDimension(new Dimension(350, 300));
        searchButton.setIcon(new ImageIcon(getClass().getResource("/GUI/images/edit.png")));
        createTable();
    }
    private void createUIComponents() {
        searchButton = new ActionButton();
    }
    private void createTable() {
        Object[][] data = {
                {"raccolta 1", null},
                {"raccolta 2", null},
                {"raccolta 3", null},
                {"raccolta 4", null},
        };
        table1.setModel(new DefaultTableModel(
            data,
            new String[]{"nome", "azioni"}
        ));
        table1.setRowHeight(40);
        table1.getColumn("azioni").setCellRenderer(new TableActionsPanelRenderer(true, true, true));
    }
    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
