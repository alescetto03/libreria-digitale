package GUI.Components;

import GUI.AppView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PresentationHallsCrudTable extends CrudTable {
    public PresentationHallsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi una sala/libreria", "Modifica una sala/libreria");
        items.getColumn("id").setMaxWidth(50);
        items.getColumn("id").setMinWidth(50);
        items.getColumn("azioni").setMaxWidth(80);
        items.getColumn("azioni").setMinWidth(80);
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
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removePresentationHallFromDatabase(Integer.parseInt((String) id));
    }

    @Override
    protected void onUpdateButton(Object id, ArrayList<String> data) {
    }

    @Override
    protected void onViewButton(Object id) {
        parentView.getAppController().showPresentedBooks(Integer.parseInt((String) id));
    }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Nome", new JTextField());
        schema.put("Indirizzo", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
