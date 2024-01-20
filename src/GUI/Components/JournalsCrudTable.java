package GUI.Components;

import GUI.AppView;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JournalsCrudTable extends CrudTable {
    public JournalsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi una rivista", "Modifica una rivista");
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
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeJournalFromDatabase((String) id);
    }

    @Override
    protected Object onUpdateButton(ArrayList<String> data) {
        return null;
    }

    @Override
    protected void onViewButton(Object id) { parentView.getAppController().showScientificPublicationsInJournal((String) id); }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Issn", new JTextField());
        schema.put("Nome", new JTextField());
        schema.put("Argomento", new JTextField());
        schema.put("Anno di pubblicazione", new JYearChooser());
        schema.put("Responsabile", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
