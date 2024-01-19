package GUI.Components;

import Controller.AppController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

public class ConferencesCrudTable extends CrudTable {
    AppController appController;
    public ConferencesCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        items.getColumn("id").setMaxWidth(50);
        items.getColumn("luogo").setMinWidth(100);
        items.getColumn("data di inizio").setMinWidth(110);
        items.getColumn("data di fine").setMinWidth(110);
        items.getColumn("organizzatore").setMinWidth(200);
        items.getColumn("responsabile").setMinWidth(120);
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("id");
            tableContent[i][1] = rowData.get("location");
            tableContent[i][2] = rowData.get("start_date");
            tableContent[i][3] = rowData.get("end_date");
            tableContent[i][4] = rowData.get("organizer");
            tableContent[i][5] = rowData.get("manager");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    protected boolean onRemoveButton(Object id) {
        return appController.removeConferenceFromDatabase((Integer.parseInt((String) id)));
    }

    @Override
    protected Object onSaveButton(ArrayList<String> data) {
        return null;
    }

    @Override
    protected void onViewButton(Object id) {

    }
}
