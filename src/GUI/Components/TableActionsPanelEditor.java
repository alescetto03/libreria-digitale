package GUI.Components;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

public class TableActionsPanelEditor extends DefaultCellEditor {
    private final ActionsPanel actionsPanel;
    public TableActionsPanelEditor(ActionsPanel actionsPanel) {
        super(new JCheckBox());
        this.actionsPanel = actionsPanel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ActionsPanel actionsPanel = new ActionsPanel(true, true, true);
        actionsPanel.setBackground(table.getSelectionBackground());
        return actionsPanel;
    }
}
