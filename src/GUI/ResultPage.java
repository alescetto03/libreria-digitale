package GUI;


import Controller.AppController;
import GUI.Components.BookPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

public class ResultPage extends AppView {

    JPanel contentPane = new JPanel();
    JLabel title = new JLabel();
    JPanel bookPanel = new JPanel();
    JLabel bookLabel = new JLabel();
    JButton bookFilterButton = new JButton();
    JScrollPane bookScrollPane = new JScrollPane();

    public ResultPage(AppController appController, ArrayList<Object> searchedBook) {
        super(appController);

        //bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.X_AXIS));
        //for(Book book : searchedBook){
           // bookPanel.add(new BookPanel(book));
       // }
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
