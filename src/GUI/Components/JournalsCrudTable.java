package GUI.Components;

import Controller.AppController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

public class JournalsCrudTable extends CrudTable {
    AppController appController;
    public JournalsCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        items.getColumn("issn").setMinWidth(70);
        items.getColumn("issn").setMaxWidth(70);
        items.getColumn("nome").setMinWidth(150);
        items.getColumn("argomento").setMinWidth(85);
        items.getColumn("anno di pubblicazione").setMinWidth(125);
        items.getColumn("responsabile").setMinWidth(125);

    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("issn");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("argument");
            tableContent[i][3] = rowData.get("publication_year");
            tableContent[i][4] = rowData.get("manager");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    protected boolean onRemoveButton(Object id) {
        return appController.removeJournalFromDatabase((String) id);
    }

    @Override
    protected boolean onSaveButton(ArrayList<String> data) {
        return false;
    }
}
