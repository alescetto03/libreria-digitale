package GUI.Components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableComboBoxRenderer extends JComboBox<String> implements TableCellRenderer {
    String currentValue;
    public TableComboBoxRenderer(String currentValue) {
        this.currentValue = currentValue;
        this.setSelectedItem(currentValue);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
