package GUI.Components;

import Controller.AppController;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.Map;

public class BookCrudTable extends CrudTable{
    AppController appController;
    public BookCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("isbn");
            tableContent[i][1] = rowData.get("title");
            tableContent[i][2] = rowData.get("publisher");
            tableContent[i][3] = rowData.get("fruition_mode");
            tableContent[i][4] = rowData.get("publication_year");
            tableContent[i][5] = rowData.get("cover");
            tableContent[i][6] = rowData.get("description");
            tableContent[i][7] = rowData.get("genre");
            tableContent[i][8] = rowData.get("target");
            tableContent[i][9] = rowData.get("topic");
            tableContent[i][10] = rowData.get("book_type");
        }

        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    protected boolean onRemoveButton(int id) {
        return false;
    }

    @Override
    protected boolean onSaveButton(ArrayList<String> data) {
        return false;
    }
}
