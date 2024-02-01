package GUI;

import GUI.Components.CrudTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ConfirmDeleteGUI extends AppView {
    private JPanel contentPane;
    private JLabel confirmMessage;
    private JButton yesButton;
    private JButton noButton;

    public ConfirmDeleteGUI(CrudTable crudTable, Object id) {
        super(crudTable.getParentView().getAppController());
        yesButton.addActionListener((ActionEvent e) -> {
            crudTable.onRemoveButton(id);
        });
        noButton.addActionListener((ActionEvent e) -> {
            crudTable.getParentView().getAppController().switchView(crudTable.getParentView());
        });
    }

    public JButton getNoButton() {
        return noButton;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
