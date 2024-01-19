package GUI.Components;

import Controller.AppController;
import Model.ScientificPublication;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.Map;

public class ScientificPublicationsCrudTable extends CrudTable{
    AppController appController;
    public ScientificPublicationsCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        String[] fruitionMode = {"digitale", "cartaceo", "audiolibro"};
        TableColumn fruitionModeColumn = items.getColumn("modalità fruizione");
        fruitionModeColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(fruitionMode)));

        items.getColumn("doi").setMinWidth(180);
        items.getColumn("titolo").setMinWidth(320);
        items.getColumn("editore").setMinWidth(180);
        items.getColumn("modalità fruizione").setMinWidth(120);
        items.getColumn("anno pubblicazione").setMinWidth(120);
        items.getColumn("descrizione").setMinWidth(300);
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("doi");
            tableContent[i][1] = rowData.get("title");
            tableContent[i][2] = rowData.get("publisher");
            tableContent[i][3] = ((ScientificPublication.FruitionMode) rowData.get("fruition_mode")).name().toLowerCase();
            tableContent[i][4] = rowData.get("publication_year");
            tableContent[i][5] = rowData.get("cover");
            tableContent[i][6] = rowData.get("description");
        }

        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    protected boolean onRemoveButton(Object id) {
        return appController.removeScientificPublicationFromDatabase((String) id);
    }

    @Override
    protected Object onSaveButton(ArrayList<String> data) {
        return null;
    }

    @Override
    protected void onViewButton(Object id) {

    }
}
