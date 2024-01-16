package GUI.Components;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

public class TableActionsPanelEditor extends DefaultCellEditor {
    private boolean displayViewButton;
    private boolean displaySaveButton;
    private boolean displayDeleteButton;
    private CrudTable crudTable;
    public TableActionsPanelEditor(CrudTable crudTable, boolean displayViewButton, boolean displaySaveButton, boolean displayDeleteButton) {
        super(new JCheckBox());
        this.displayViewButton = displayViewButton;
        this.displaySaveButton = displaySaveButton;
        this.displayDeleteButton = displayDeleteButton;
        this.crudTable = crudTable;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ActionsPanel actionsPanel = new ActionsPanel(crudTable, displayViewButton, displaySaveButton, displayDeleteButton);
        actionsPanel.setBackground(table.getSelectionBackground());
        return actionsPanel;
    }
}
