package GUI;


import Controller.AppController;
import GUI.Components.BookPanel;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

public class ResultPage extends AppView {

    JPanel contentPane = new JPanel();
    JLabel title = new JLabel();
    JPanel bookPanel = new JPanel();
    JPanel titleBookPanel = new JPanel();
    JLabel bookLabel = new JLabel("Libri:");
    JButton bookFilterButton = new JButton();
    JScrollPane bookScrollPane;

    public ResultPage(AppController appController, ArrayList<Map<String, Object>> searchedBook) {
        super(appController);
        titleBookPanel.setLayout(new FlowLayout());
        bookLabel.setHorizontalAlignment(JLabel.LEFT);
        titleBookPanel.add(bookLabel);


        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.X_AXIS));

        for(Map<String, Object> book : searchedBook){
            bookPanel.add(new BookPanel((String) book.get("title"), (BufferedImage) book.get("cover")));
        }
        bookScrollPane = new JScrollPane(bookPanel);
        bookScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        bookScrollPane.add(titleBookPanel);
        bookScrollPane.setPreferredSize(new Dimension(400, 200));
        contentPane.setPreferredSize(new Dimension(600, 300));
        contentPane.add(bookScrollPane);
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
