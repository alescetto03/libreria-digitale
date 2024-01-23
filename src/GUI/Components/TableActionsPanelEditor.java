package GUI.Components;

import javax.swing.*;
import java.awt.*;

public class TableActionsPanelEditor extends DefaultCellEditor {
    private boolean displayViewButton;
    private boolean displayEditButton;
    private boolean displayDeleteButton;
    private CrudTable crudTable;
    public TableActionsPanelEditor(CrudTable crudTable, boolean displayViewButton, boolean displayEditButton, boolean displayDeleteButton) {
        super(new JCheckBox());
        this.displayViewButton = displayViewButton;
        this.displayEditButton = displayEditButton;
        this.displayDeleteButton = displayDeleteButton;
        this.crudTable = crudTable;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ActionsPanel actionsPanel = new ActionsPanel(crudTable, displayViewButton, displayEditButton, displayDeleteButton);
        actionsPanel.setBackground(table.getSelectionBackground());
        return actionsPanel;
    }
}
