package GUI.Components;

import GUI.AppView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeriesCrudTable extends CrudTable {
    public SeriesCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, false, true, true, true, "Aggiungi un libro in una serie", "Modifica un libro in una serie");
        items.getColumn("prequel").setMaxWidth(100);
        items.getColumn("prequel").setMinWidth(100);
        items.getColumn("sequel").setMaxWidth(100);
        items.getColumn("sequel").setMinWidth(100);
        items.getColumn("azioni").setMaxWidth(60);
        items.getColumn("azioni").setMinWidth(60);
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("prequel");
            tableContent[i][1] = rowData.get("sequel");
            tableContent[i][2] = rowData.get("name");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeSerieFromDatabase((String) id);
    }

    @Override
    protected void onUpdateButton(Object id, ArrayList<String> data) {
    }

    @Override
    protected void onViewButton(Object id) {

    }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Nome", new JTextField());
        schema.put("Prequel", new JTextField());
        schema.put("Sequel", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
