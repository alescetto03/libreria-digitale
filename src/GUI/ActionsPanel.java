package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Classe GUI che rappresenta un JPanel contenente dei pulsanti:
 * - pulsante "modifica";
 * - pulsante "elimina";
 * - pulsante "visualizza";
 */
public class ActionsPanel extends JPanel{
    private ActionButton viewButton;
    private ActionButton editButton;
    private ActionButton deleteButton;
    private CrudTable crudTable;
    boolean displayViewButton;
    boolean displayEditButton;
    boolean displayDeleteButton;

    public ActionsPanel(CrudTable crudTable, boolean displayViewButton, boolean displayEditButton, boolean displayDeleteButton) {
        this.crudTable = crudTable;
        this.displayViewButton = displayViewButton;
        this.displayEditButton = displayEditButton;
        this.displayDeleteButton = displayDeleteButton;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);

        if (displayViewButton) {
            viewButton = new ActionButton("/GUI/images/view.png", 18, 18, Image.SCALE_SMOOTH);
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
        if (displayEditButton) {
            editButton = new ActionButton("/GUI/images/edit.png", 18, 18, Image.SCALE_SMOOTH);
            add(editButton, gbc);
            editButton.addActionListener((ActionEvent e) -> {
                int row = crudTable.items.getSelectedRow();
                Object id = crudTable.items.getValueAt(row, 0);
                ArrayList<String> data = new ArrayList<>();
                for (int i = 0; i < crudTable.items.getColumnCount() - 1; i++) {
                    data.add(String.valueOf(crudTable.items.getValueAt(row, i)));
                }
                crudTable.onUpdateButton(id, data);
            });
        }
        if (displayDeleteButton) {
            deleteButton = new ActionButton("/GUI/images/delete.png", 18, 18, Image.SCALE_SMOOTH);
            add(deleteButton, gbc);
            deleteButton.addActionListener((ActionEvent e) -> {
                int row = crudTable.items.getSelectedRow();
                Object id = crudTable.items.getValueAt(row, 0);
                crudTable.parentView.getAppController().switchView(new ConfirmDeleteGUI(crudTable, id));
            });
        }
    }

    public CrudTable getCrudTable() { return crudTable; }
}