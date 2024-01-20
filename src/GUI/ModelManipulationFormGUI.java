package GUI;

import Controller.AppController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
            entry.getValue().setMaximumSize(new Dimension(300, 50));

            formField.add(label, BorderLayout.PAGE_START);
            formField.add(entry.getValue(), BorderLayout.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
            formField.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            form.add(formField, BorderLayout.CENTER);
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

    public Map<String, String> getFormData() {
        Map<String, String> renderedData = new HashMap<>();
        for (Map.Entry<String, JComponent> entry : data.entrySet()) {
            if (entry.getValue() instanceof JTextField) {
                renderedData.put(entry.getKey(), ((JTextField) entry.getValue()).getText().trim());
            } else if (entry.getValue() instanceof JComboBox<?>) {
                renderedData.put(entry.getKey(), (((JComboBox<?>) entry.getValue()).getSelectedItem()).toString());
            }
        }
        return renderedData;
    }
}
