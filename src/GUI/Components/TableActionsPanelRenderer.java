package GUI.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableActionsPanelRenderer extends DefaultTableCellRenderer {
    private boolean displayViewButton;
    private boolean displayEditButton;
    private boolean displayCreateButton;
    private boolean displayDeleteButton;
    private CrudTable crudTable;
    public TableActionsPanelRenderer(CrudTable crudTable, boolean displayViewButton, boolean displayEditButton, boolean displayCreateButton, boolean displayDeleteButton) {
        this.displayViewButton = displayViewButton;
        this.displayEditButton = displayEditButton;
        this.displayCreateButton = displayCreateButton;
        this.displayDeleteButton = displayDeleteButton;
        this.crudTable = crudTable;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ActionsPanel actionsPanel = new ActionsPanel(crudTable, displayViewButton, displayEditButton, displayCreateButton, displayDeleteButton);
        actionsPanel.setBackground(component.getBackground());
        return actionsPanel;
    }
}
