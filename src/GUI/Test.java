package GUI;

import Controller.AppController;

import javax.swing.*;

public class Test extends AppView{
    public Test(AppController appController) {
        super(appController);
        JScrollPane scrollPane = new JScrollPane();
    }

    @Override
    public JPanel getContentPane() {
        return null;
    }
}
