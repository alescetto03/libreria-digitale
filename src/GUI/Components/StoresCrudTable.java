package GUI.Components;

import GUI.AppView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoresCrudTable extends CrudTable {
    public StoresCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, false, true, true, true, "Aggiungi un negozio", "Modifica un negozio");
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
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeStoreFromDatabase((String) id);
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
        schema.put("Partita Iva", new JTextField());
        schema.put("Nome", new JTextField());
        schema.put("Indirizzo", new JTextField());
        schema.put("Url", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
