package GUI;

import GUI.Components.IconButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePages {
    private JList list1;
    private JList list2;
    private JTextField textField1;
    private JButton button1;
    private JButton button2;

    public HomePages() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        button1 = new IconButton("/GUI/images/search_icon.png",30, 30, Image.SCALE_SMOOTH);

    }
}
