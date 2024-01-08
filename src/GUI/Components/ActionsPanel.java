package GUI.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class ActionsPanel extends JPanel{
    private ActionButton viewButton;
    private ActionButton editButton;
    private ActionButton createButton;
    private ActionButton deleteButton;
    private CrudTable crudTable;

    public ActionsPanel(CrudTable crudTable, boolean displayViewButton, boolean displayEditButton, boolean displayCreateButton, boolean displayDeleteButton) {
        this.crudTable = crudTable;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        if (displayViewButton) {
            viewButton = new ActionButton();
            viewButton.setIcon(new ImageIcon(getClass().getResource("/GUI/images/view.png")));
            add(viewButton, gbc);
        }
        if (displayEditButton) {
            editButton = new ActionButton();
            editButton.setIcon(new ImageIcon(getClass().getResource("/GUI/images/save.png")));
            add(editButton, gbc);
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
        }
    }

    public CrudTable getCrudTable() { return crudTable; }
}
