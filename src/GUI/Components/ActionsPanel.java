package GUI.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class ActionsPanel extends JPanel{
    private ActionButton viewButton;
    private ActionButton saveButton;
    private ActionButton createButton;
    private ActionButton deleteButton;
    private CrudTable crudTable;

    public ActionsPanel(CrudTable crudTable, boolean displayViewButton, boolean displaySaveButton, boolean displayCreateButton, boolean displayDeleteButton) {
        this.crudTable = crudTable;
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
                JTable table = (JTable) ((JViewport) ((JScrollPane) crudTable.getComponent(1)).getComponent(0)).getComponent(0);
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

            });
        }
        if (displayCreateButton) {
            createButton = new ActionButton();
            createButton.setIcon(new ImageIcon(getClass().getResource("/GUI/images/create.png")));
            add(createButton, gbc);
            createButton.addActionListener((ActionEvent e) -> {
                JTable table = (JTable) ((JViewport) ((JScrollPane) crudTable.getComponent(1)).getComponent(0)).getComponent(0);
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

                int columnCount = table.getColumnCount();
                String[] emptyRow = new String[columnCount];
                Arrays.fill(emptyRow, "");
                tableModel.insertRow(0, emptyRow);
            });
        }
        if (displayDeleteButton) {
            deleteButton = new ActionButton();
            deleteButton.setIcon(new ImageIcon(getClass().getResource("/GUI/images/delete.png")));
            add(deleteButton, gbc);
            deleteButton.addActionListener((ActionEvent e) -> {
                JTable table = (JTable) ((JViewport) ((JScrollPane) crudTable.getComponent(1)).getComponent(0)).getComponent(0);
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

                for (int row: table.getSelectedRows()) {
                    String id = (String) table.getValueAt(row, 0);
                    if (id.equals("") || crudTable.onRemoveButton(row, id)) {
                        tableModel.removeRow(row);
                    }
                }
            });
        }
    }

    public CrudTable getCrudTable() { return crudTable; }
}
