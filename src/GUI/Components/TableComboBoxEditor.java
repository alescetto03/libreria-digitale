package GUI.Components;

import javax.swing.*;

public class TableComboBoxEditor extends DefaultCellEditor {
    String currentValue;

    public TableComboBoxEditor(String currentValue, JComboBox<String> comboBox) {
        super(comboBox);
        this.currentValue = currentValue;
    }
}
