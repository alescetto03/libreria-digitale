package GUI;

import Controller.AppController;

import javax.swing.*;
import java.util.ArrayList;

public class CollectionsGUI extends AppView{
    JPanel contentPane = new JPanel();

    public CollectionsGUI(AppController appController) {
        super(appController);
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
