package GUI.Components;

import Controller.AppController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

public class AuthorsCrudTable extends CrudTable {
    AppController appController;
    public AuthorsCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        items.getColumn("id").setMaxWidth(50);
        items.getColumn("nome").setMinWidth(100);
        items.getColumn("data di nascita").setMinWidth(110);
        items.getColumn("data di morte").setMinWidth(110);
        items.getColumn("nazionalità").setMinWidth(100);
        items.getColumn("biografia").setMinWidth(300);
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("id");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("birth_date");
            tableContent[i][3] = rowData.get("death_date");
            tableContent[i][4] = rowData.get("nationality");
            tableContent[i][5] = rowData.get("bio");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    protected boolean onRemoveButton(Object id) {
        return appController.removeAuthorFromDatabase(Integer.parseInt((String) id));
    }

    @Override
    protected boolean onSaveButton(ArrayList<String> data) {
        return false;
    }
}
