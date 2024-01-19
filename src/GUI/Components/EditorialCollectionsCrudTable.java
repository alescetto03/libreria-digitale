package GUI.Components;

import Controller.AppController;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

public class EditorialCollectionsCrudTable extends CrudTable {
    AppController appController;
    public EditorialCollectionsCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("issn");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("publisher");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    protected boolean onRemoveButton(Object id) {
        return appController.removeEditorialCollectionFromDatabase((String) id);
    }

    @Override
    protected Object onSaveButton(ArrayList<String> data) {
        return null;
    }

    @Override
    protected void onViewButton(Object id) {
    }
}
