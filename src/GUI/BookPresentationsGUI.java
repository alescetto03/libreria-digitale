package GUI;

import Controller.AppController;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class BookPresentationsGUI extends AppView {
    JPanel contentPane = new JPanel();
    JPanel titlePanel = new JPanel();
    JLabel presentationHallTitle = new JLabel();
    public BookPresentationsGUI(AppController appController, Map<String, Object> presentationHall, ArrayList<Map<String, Object>> bookPresentations, ArrayList<Map<String, Object>> books, AppView parentView) {
        super(appController);
        JButton goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(new AdminPageGUI(appController, new PresentationHallsCrudTable(parentView, "Librerie:", new String[]{"id", "nome", "indirizzo"}, appController.getRenderedPresentationHalls())));
        });

        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        presentationHallTitle.setText((String) presentationHall.get("name"));
        presentationHallTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(goBackButton);
        titlePanel.add(presentationHallTitle);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(800, 500));

        JPanel itemsWrapper = new JPanel();
        itemsWrapper.setLayout(new BoxLayout(itemsWrapper, BoxLayout.Y_AXIS));
        itemsWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        ArrayList<JPanel> items = new ArrayList<>();
        for (Map<String, Object> book: books) {
            JPanel item = new JPanel();
            JLabel isbn = new JLabel(book.get("isbn") + " | ");
            JLabel title = new JLabel((String) book.get("title"));

            JCheckBox checkBox = new JCheckBox();
            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setPreferredSize(new Dimension(150, 20));
            dateChooser.setVisible(false);
            ActionButton saveButton = new ActionButton("/GUI/images/save.png", 18, 18, Image.SCALE_SMOOTH);
            for (Map<String, Object> bookPresentation: bookPresentations) {
                if (bookPresentation.get("presented_book").equals(book)) {
                    checkBox.setSelected(true);
                    dateChooser.setDate(Date.from(((LocalDate) bookPresentation.get("presentation_date")).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    dateChooser.setVisible(true);
                    break;
                }
            }

            item.setLayout(new FlowLayout(FlowLayout.LEFT));
            item.add(isbn);
            item.add(title);
            item.add(checkBox);
            item.add(dateChooser);
            item.add(saveButton);
            items.add(item);
            itemsWrapper.add(item);

            checkBox.addActionListener((ActionEvent e) -> {
                dateChooser.setVisible(checkBox.isSelected());
            });
            saveButton.addActionListener((ActionEvent e) -> {
                LocalDate parsedPresentationDate = dateChooser.getDate() != null ? dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                try {
                    appController.updateBookPresentation((String) book.get("isbn"), (int) presentationHall.get("id"), parsedPresentationDate, ((JCheckBox) saveButton.getParent().getComponent(2)).isSelected());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        contentPane.add(titlePanel, BorderLayout.PAGE_START);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(itemsWrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
