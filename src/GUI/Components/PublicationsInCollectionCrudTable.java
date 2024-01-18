package GUI.Components;

import Controller.AppController;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

public class PublicationsInCollectionCrudTable extends CrudTable{
    AppController appController;
    int collectiond_id;
    public PublicationsInCollectionCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data, Integer collection_id, String collection_owner) {
        super(title, columns, data, false, false, false, collection_owner.equals(appController.getLoggedUsername()));
        this.appController = appController;
        this.collectiond_id = collection_id;

        if(!collection_owner.equals(appController.getLoggedUsername())) {
            items.getColumn("azioni").setMinWidth(0);
            items.getColumn("azioni").setMaxWidth(0);
        }

    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("doi");
            tableContent[i][1] = rowData.get("title");
            tableContent[i][2] = rowData.get("publisher");
            tableContent[i][3] = rowData.get("fruition_mode");
            tableContent[i][4] = rowData.get("publication_year");
            tableContent[i][5] = rowData.get("description");
        }
        return new DefaultTableModel(tableContent, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column == getColumnCount() - 1); // Ultima colonna (azioni) è modificabile
            }
        };
    }

    @Override
    protected boolean onRemoveButton(Object doi) {
        return appController.removePublicationFromCollection(doi, this.collectiond_id);
    }

    @Override
    protected boolean onSaveButton(ArrayList<String> data) {
        return false;
    }

    @Override
    protected void onViewButton(Object id) {

    }
}
