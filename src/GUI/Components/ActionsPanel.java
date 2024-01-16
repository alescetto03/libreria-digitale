package GUI.Components;

import Controller.AppController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ActionsPanel extends JPanel{
    private ActionButton viewButton;
    private ActionButton saveButton;
    private ActionButton deleteButton;
    private CrudTable crudTable;
    boolean displayCreateButton;
    boolean displayViewButton;
    boolean displaySaveButton;
    boolean displayDeleteButton;

    public ActionsPanel(CrudTable crudTable, boolean displayCreateButton, boolean displayViewButton, boolean displaySaveButton, boolean displayDeleteButton) {
        this.crudTable = crudTable;
        this.displayCreateButton = displayCreateButton;
        this.displayViewButton = displayViewButton;
        this.displaySaveButton = displaySaveButton;
        this.displayDeleteButton = displayDeleteButton;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);

        if (displayViewButton) {
            viewButton = new ActionButton();
            viewButton.setIcon(new ImageIcon(getClass().getResource("/GUI/images/view.png")));
            add(viewButton, gbc);
        }
        if (displaySaveButton) {
            saveButton = new ActionButton();
            saveButton.setIcon(new ImageIcon(getClass().getResource("/GUI/images/save.png")));
            add(saveButton, gbc);
            saveButton.addActionListener((ActionEvent e) -> {
                JTable table = (JTable) saveButton.getParent().getParent();
                int row = table.getEditingRow();
                ArrayList<String> data = new ArrayList<>();
                for (int i = 0; i < table.getColumnCount() - 1; i++) {
                    data.add(String.valueOf(table.getValueAt(row, i)));
                }
                System.out.println(data);
                if (!crudTable.onSaveButton(data)) {
                    JOptionPane.showMessageDialog(crudTable, "Errore durante il salvataggio", "Errore!!!", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        if (displayDeleteButton) {
            deleteButton = new ActionButton();
            deleteButton.setIcon(new ImageIcon(getClass().getResource("/GUI/images/delete.png")));
            add(deleteButton, gbc);
            deleteButton.addActionListener((ActionEvent e) -> {
                JTable table = (JTable) deleteButton.getParent().getParent();
                int row = table.getEditingRow();
                String id = String.valueOf(table.getValueAt(row, 0));
                if (id.equals("") || crudTable.onRemoveButton(Integer.parseInt(id))) {
                    ((DefaultTableModel) table.getModel()).removeRow(row);
                }
            });
        }
    }

    public CrudTable getCrudTable() { return crudTable; }
}
