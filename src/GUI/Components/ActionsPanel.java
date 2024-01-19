package GUI.Components;

import Controller.AppController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ActionsPanel extends JPanel{
    private IconButton viewButton;
    private IconButton saveButton;
    private IconButton deleteButton;
    private CrudTable crudTable;
    boolean displayViewButton;
    boolean displaySaveButton;
    boolean displayDeleteButton;

    public ActionsPanel(CrudTable crudTable, boolean displayViewButton, boolean displaySaveButton, boolean displayDeleteButton) {
        this.crudTable = crudTable;
        this.displayViewButton = displayViewButton;
        this.displaySaveButton = displaySaveButton;
        this.displayDeleteButton = displayDeleteButton;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);

        if (displayViewButton) {
            viewButton = new IconButton("/GUI/images/view.png", 18, 18, Image.SCALE_SMOOTH);
            add(viewButton, gbc);
            viewButton.addActionListener((ActionEvent e) -> {
                JTable table = (JTable) viewButton.getParent().getParent();
                int row = table.getEditingRow();
                String id = String.valueOf(table.getValueAt(row, 0));
                if (!id.isEmpty()){
                    crudTable.onViewButton(id);
                }
            });
        }
        if (displaySaveButton) {
            saveButton = new IconButton("/GUI/images/save.png", 18, 18, Image.SCALE_SMOOTH);
            add(saveButton, gbc);
            saveButton.addActionListener((ActionEvent e) -> {
                JTable table = (JTable) saveButton.getParent().getParent();
                int row = table.getEditingRow();
                ArrayList<String> data = new ArrayList<>();
                for (int i = 0; i < table.getColumnCount() - 1; i++) {
                    data.add(String.valueOf(table.getValueAt(row, i)));
                }
                try {
                    if (!crudTable.onSaveButton(data)) {
                        JOptionPane.showMessageDialog(crudTable, "Errore durante il salvataggio", "Errore!!!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(crudTable, exception.getMessage(), "Errore!!!", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        if (displayDeleteButton) {
            deleteButton = new IconButton("/GUI/images/delete.png", 18, 18, Image.SCALE_SMOOTH);
            add(deleteButton, gbc);
            deleteButton.addActionListener((ActionEvent e) -> {
                JTable table = (JTable) deleteButton.getParent().getParent();
                int row = table.getEditingRow();
                String id = String.valueOf(table.getValueAt(row, 0));
                try {
                    if (id.isEmpty() || crudTable.onRemoveButton(id)) {
                        ((DefaultTableModel) table.getModel()).removeRow(row);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(crudTable, exception.getMessage(), "Errore!!!", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    public CrudTable getCrudTable() { return crudTable; }
}