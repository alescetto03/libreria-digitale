package GUI.Components;

import Controller.AppController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

public class StoresCrudTable extends CrudTable {
    AppController appController;
    public StoresCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        items.getColumn("partita iva").setMinWidth(90);
        items.getColumn("partita iva").setMaxWidth(90);
        items.getColumn("nome").setMinWidth(150);
        items.getColumn("indirizzo").setMinWidth(180);
        items.getColumn("url").setMinWidth(200);
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("partita_iva");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("address");
            tableContent[i][3] = rowData.get("url");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    protected boolean onRemoveButton(Object id) {
        return appController.removeStoreFromDatabase((String) id);
    }

    @Override
    protected boolean onSaveButton(ArrayList<String> data) {
        return false;
    }
}
