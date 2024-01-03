package GUI.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableActionsPanelRenderer extends DefaultTableCellRenderer {
    private final ActionsPanel actionsPanel;
    public TableActionsPanelRenderer(ActionsPanel actionsPanel) {
        this.actionsPanel = actionsPanel;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ActionsPanel actionsPanel = new ActionsPanel(true, true, true);
        actionsPanel.setBackground(component.getBackground());
        return actionsPanel;
    }
}
