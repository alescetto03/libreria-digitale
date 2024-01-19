package GUI.Components;

import Controller.AppController;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

public class SeriesCrudTable extends CrudTable {
    AppController appController;
    public SeriesCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
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

    @Override
    protected boolean onRemoveButton(Object id) {
        boolean isDeleted = appController.removeSerieFromDatabase((String) id);
        if (isDeleted) {
            DefaultTableModel model = (DefaultTableModel) items.getModel();
            model.setRowCount(0); // Rimuovi tutte le righe esistenti

            ArrayList<Map<String, Object>> series = appController.getRenderedSeries();
            setData(series);
            DefaultTableModel newModel = getModel();
            System.out.println(newModel);
            items.setModel(newModel);
        }
        return isDeleted;
    }

    @Override
    protected Object onSaveButton(ArrayList<String> data) {
        return null;
    }

    @Override
    protected void onViewButton(Object id) {

    }
}
