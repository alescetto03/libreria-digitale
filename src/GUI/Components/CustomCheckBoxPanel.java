package GUI.Components;

import Controller.AppController;
import GUI.AppView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomCheckBoxPanel extends JPanel {
    private Map<String, JCheckBox> checkBoxMap = new HashMap<>();
    AppController appController;

    JButton confirmButton = new JButton("Conferma");
    boolean check_selection = false;

    public CustomCheckBoxPanel(AppController appController, ArrayList<Map<String, Object>> personalCollection, String book_isbn, String publication_doi, AppView previousView) {
        this.appController = appController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (Map<String, Object> label : personalCollection) {
            JCheckBox checkBox = new JCheckBox(label.get("name").toString());
            checkBoxMap.put(label.get("name").toString(), checkBox);
            add(checkBox);
        }


        confirmButton.addActionListener((ActionEvent e) -> {
            for (Map.Entry<String, JCheckBox> entry : checkBoxMap.entrySet()) {
                String label = entry.getKey();
                JCheckBox checkBox = entry.getValue();
                if (checkBox.isSelected()) {
                    if (!personalCollection.isEmpty()) {
                        check_selection = true;
                        for (Map<String, Object> item : personalCollection) {
                            try {
                                if ((item.get("name").toString().equals(label)) && !(book_isbn.isEmpty())) {
                                    if (!appController.saveBookInCollection(item.get("id").hashCode(), book_isbn))
                                        JOptionPane.showMessageDialog(this, "Il libro che stai cercando di salvare esiste gia' nelle tue raccolte.", "!!! ATTENZIONE !!!", JOptionPane.WARNING_MESSAGE);
                                    else
                                        JOptionPane.showMessageDialog(this, "Il libro e' stato inserito con successo nella raccolta.", "CONGRATULAZIONI", JOptionPane.INFORMATION_MESSAGE);
                                } else if ((item.get("name").toString().equals(label)) && !(publication_doi.isEmpty())) {
                                    if (!appController.savePublicationInCollection(item.get("id").hashCode(), publication_doi))
                                        JOptionPane.showMessageDialog(this, "L'articolo che stai cercando di salvare esiste gia' nelle tue raccolte.", "!!! ATTENZIONE !!!", JOptionPane.WARNING_MESSAGE);
                                    else
                                        JOptionPane.showMessageDialog(this, "L'articolo e' stato inserito con successo nella raccolta.", "CONGRATULAZIONI", JOptionPane.INFORMATION_MESSAGE);
                                } else
                                    throw new Exception();
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Non ci sono raccolte per questo utente. Verrai reindirizzato alla pagina precedente !", "!!! ERRORE !!!", JOptionPane.ERROR_MESSAGE);
                        try {
                            wait(10000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        appController.switchView(previousView);
                    }

                }
            }
            if (!check_selection)
                JOptionPane.showMessageDialog(this, "Non hai selezionato nessuna raccolta, selezionane una oppure torna indietro.", "!!! ATTENZIONE !!!", JOptionPane.WARNING_MESSAGE);
        });
        add(confirmButton);
    }

}
