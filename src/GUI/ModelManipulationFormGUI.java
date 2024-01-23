package GUI;

import Controller.AppController;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModelManipulationFormGUI extends AppView {
    private JButton confirmButton;
    private JButton goBackButton;
    JPanel contentPane = new JPanel();
    Map<String, JComponent> data;
    public ModelManipulationFormGUI(AppController appController, AppView parentView, Map<String, JComponent> data, String title) {
        super(appController);
        this.data = data;
        int marginSize = 10;
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(400, 500));
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        for (Map.Entry<String, JComponent> entry : data.entrySet()) {
            JPanel formField = new JPanel();
            formField.setLayout(new BorderLayout());
            JLabel label = new JLabel(entry.getKey());
            formField.add(label, BorderLayout.PAGE_START);
            formField.add(entry.getValue(), BorderLayout.PAGE_END);
            formField.setMaximumSize(new Dimension(400, 55));

            formField.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            form.add(formField);
        }
        confirmButton = new JButton("Conferma");
        JScrollPane scrollPane = new JScrollPane(form);

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout());
        goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(parentView);
        });
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        topBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        topBar.add(goBackButton);
        topBar.add(titleLabel);

        contentPane.add(topBar, BorderLayout.PAGE_START);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(confirmButton, BorderLayout.PAGE_END);
    }

    public ModelManipulationFormGUI(AppController appController, AppView parentView, Map<String, JComponent> data, String title, Object id) {
        this(appController, parentView, data, title);
    }
    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getGoBackButton() { return goBackButton; }

    public Map<String, String> getFormData() throws Exception{
        Map<String, String> renderedData = new HashMap<>();
        for (Map.Entry<String, JComponent> entry : data.entrySet()) {
            if (entry.getValue() instanceof JTextField) {
                renderedData.put(entry.getKey(), ((JTextField) entry.getValue()).getText().trim());
            } else if (entry.getValue() instanceof JComboBox<?>) {
                renderedData.put(entry.getKey(), (((JComboBox<?>) entry.getValue()).getSelectedItem()).toString());
            } else if (entry.getValue() instanceof JYearChooser){
                renderedData.put(entry.getKey(), String.valueOf((((JYearChooser) entry.getValue()).getValue())));
            } else if (entry.getValue() instanceof JTextArea){
                renderedData.put(entry.getKey(), ((JTextArea) entry.getValue()).getText().trim());
            } else if (entry.getValue() instanceof JDateChooser){
                if (((JDateChooser) entry.getValue()).getDate() != null)
                    renderedData.put(entry.getKey(), String.valueOf((((JDateChooser) entry.getValue()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())));
                else if (entry.getKey().equals("Data di nascita") && ((JDateChooser) entry.getValue()).getDate() == null)
                    throw new Exception("Il campo data di nascita non puo' essere nullo.");
                else if ((entry.getKey().equals("Data di inizio") || entry.getKey().equals("Data di fine")) && ((JDateChooser) entry.getValue()).getDate() == null)
                    throw new Exception("I campi Data di inizio e Data di fine non possono essere nulli.");
                else
                    renderedData.put(entry.getKey(), "");
            }

        }
        return renderedData;
    }
}
