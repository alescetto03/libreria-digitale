package GUI.Components;

import Controller.AppController;
import Model.Collection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.Map;

public class PersonalCollectionsCrudTable extends CrudTable {
    AppController appController;
    public PersonalCollectionsCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, true, true, true, true);
        this.appController = appController;
        TableColumn idColumn = items.getColumn("id");
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        String[] visibilities = {"pubblica", "privata"};
        TableColumn visibilityColumn = items.getColumn("visibilita");
        visibilityColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(visibilities)));
        for (int i = 0; i < items.getRowCount(); i++) {
            items.getValueAt(i, visibilityColumn.getModelIndex());
        }
    }
    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("id");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = ((Collection.Visibility) rowData.get("visibility")).name().toLowerCase();
        }
        return new DefaultTableModel(tableContent, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column == getColumnCount() - 1); // Ultima colonna (azioni) è modificabile
            }
        };
    }

    @Override
    public boolean onRemoveButton(Object id) { return appController.removeCollectionFromDatabase(id); }

    @Override
    protected boolean onSaveButton(ArrayList<String> data) { return appController.savePersonalCollectionIntoDatabase(data); }

    @Override
    protected void onViewButton(Object id) {
        appController.showCollectionItems(id);
    }

}
