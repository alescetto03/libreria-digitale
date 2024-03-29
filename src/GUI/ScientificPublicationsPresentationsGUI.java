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

/**
 * View che permette all'utente di gestire le presentazioni di articoli scientifici durante una conferenza
 */
public class ScientificPublicationsPresentationsGUI extends AppView{
    JPanel contentPane = new JPanel();
    JPanel titlePanel = new JPanel();
    JLabel conferenceTitle = new JLabel();
    public ScientificPublicationsPresentationsGUI(AppController appController, Map<String, Object> conference, ArrayList<Map<String, Object>> bookPresentations, ArrayList<Map<String, Object>> scientificPublications, AppView parentView) {
        super(appController);
        JButton goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(new AdminPageGUI(appController, new ConferencesCrudTable(parentView, "Conferenze:", new String[]{"id", "luogo", "data di inizio", "data di fine", "organizzatore", "responsabile"}, appController.getRenderedConferences())));
        });

        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        conferenceTitle.setText("<html>" + conference.get("organizer") + " - " + conference.get("location") + "<br>" + conference.get("start_date") + " - " + conference.get("end_date") + "</html>");
        conferenceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(goBackButton);
        titlePanel.add(conferenceTitle);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(800, 500));

        JPanel itemsWrapper = new JPanel();
        itemsWrapper.setLayout(new BoxLayout(itemsWrapper, BoxLayout.Y_AXIS));
        itemsWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        ArrayList<JPanel> items = new ArrayList<>();
        for (Map<String, Object> scientificPublication: scientificPublications) {
            JPanel item = new JPanel();
            JLabel isbn = new JLabel(scientificPublication.get("doi") + " | ");
            JLabel title = new JLabel((String) scientificPublication.get("title"));

            JCheckBox checkBox = new JCheckBox();
            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setPreferredSize(new Dimension(150, 20));
            dateChooser.setMinSelectableDate(Date.from(((LocalDate) conference.get("start_date")).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            dateChooser.setMaxSelectableDate(Date.from(((LocalDate) conference.get("end_date")).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            dateChooser.setDate(Date.from(((LocalDate) conference.get("start_date")).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            dateChooser.setVisible(false);
            ActionButton saveButton = new ActionButton("/GUI/images/save.png", 18, 18, Image.SCALE_SMOOTH);
            for (Map<String, Object> bookPresentation: bookPresentations) {
                if (bookPresentation.get("presented_scientific_publication").equals(scientificPublication)) {
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
                    appController.updateScientificPublicationPresentation((String) scientificPublication.get("doi"), (int) conference.get("id"), parsedPresentationDate, ((JCheckBox) saveButton.getParent().getComponent(2)).isSelected());
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
