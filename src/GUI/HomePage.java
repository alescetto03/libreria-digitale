package GUI;

import Controller.AppController;

import javax.swing.*;
import java.awt.*;

public class HomePage extends AppView{
    private JTextField searchBar;
    private JButton searchButton;
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
        Font titleFont = UIManager.getDefaults().getFont("Galleria.Login.Title.font");
        personalCollectionLabel.setFont(titleFont);
        savedCollectionLabel.setFont(titleFont);
    }

    private void createUIComponents() {
        //button1 = new IconButton("/GUI/images/search_icon.png",30, 30, Image.SCALE_SMOOTH);

    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
