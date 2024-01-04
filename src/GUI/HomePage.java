package GUI;

import Controller.AppController;
import GUI.Components.IconButton;
import com.sun.source.util.JavacTask;

import javax.swing.*;
import java.awt.*;

public class HomePage extends AppView{
    private JTextField searchBar;
    private IconButton notificationButton;
    private IconButton searchButton;
    private IconButton accountButton;
    private JLabel personalCollectionLabel;
    //private JLabel savedCollectionLabel;
    private JPanel contentPane;
    private JTable table1;
    private JButton button1;

    public HomePage(AppController appController) {
        super(appController);
        setTitle("Home Page");
        setDimension(new Dimension(450, 450));

        Font titleFont = UIManager.getDefaults().getFont("Arial");
        personalCollectionLabel.setFont(titleFont);
        //savedCollectionLabel.setFont(titleFont);
    }

    private void createUIComponents() {
        //button1 = new IconButton("/GUI/images/search_icon.png",30, 30, Image.SCALE_SMOOTH);
        searchButton = new IconButton("/GUI/images/search_icon.png",30, 30, Image.SCALE_SMOOTH);
        notificationButton = new IconButton("/GUI/images/notification_icon.png",30, 30, Image.SCALE_SMOOTH);
        accountButton = new IconButton("/GUI/images/account_icon.png",30, 30, Image.SCALE_SMOOTH);
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
