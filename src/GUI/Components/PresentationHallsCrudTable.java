package GUI.Components;

import Controller.AppController;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

public class PresentationHallsCrudTable extends CrudTable {
    AppController appController;
    public PresentationHallsCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
        items.getColumn("id").setMaxWidth(50);
        items.getColumn("id").setMinWidth(50);
        items.getColumn("azioni").setMaxWidth(60);
        items.getColumn("azioni").setMinWidth(60);
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("id");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("address");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    protected boolean onRemoveButton(Object id) {
        return appController.removePresentationHallFromDatabase(Integer.parseInt((String) id));
    }

    @Override
    protected boolean onSaveButton(ArrayList<String> data) {
        return false;
    }
}
