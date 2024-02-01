package GUI.Components;

import GUI.AppView;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.Map;

public class SavedCollectionCrudTable extends CrudTable{
    public SavedCollectionCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, false, false, true);

        TableColumn idColumn = items.getColumn("id");
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);

    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("id");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("owner");
        }

        return new DefaultTableModel(tableContent, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column == getColumnCount() - 1); // Ultima colonna (azioni) è modificabile
            }
        };
    }

    @Override
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeSavedCollectionFromDatabase(id);
    }

    @Override
    protected void onUpdateButton(Object id, ArrayList<String> data) {
    }

    @Override
    protected void onViewButton(Object id) {
        parentView.getAppController().showCollectionItems(id);
    }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        return null;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
