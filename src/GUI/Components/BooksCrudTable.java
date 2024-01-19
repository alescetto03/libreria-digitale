package GUI.Components;

import Controller.AppController;
import Model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.Map;

public class BooksCrudTable extends CrudTable{
    AppController appController;
    public BooksCrudTable(AppController appController, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(title, columns, data, false, true, true, true);
        this.appController = appController;
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        String[] bookType = {"didattico", "romanzo"};
        String[] fruitionMode = {"digitale", "cartaceo", "audiolibro"};

        TableColumn fruitionModeColumn = items.getColumn("modalità fruizione");
        TableColumn bookTypeColumn = items.getColumn("tipo");

        fruitionModeColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(fruitionMode)));
        bookTypeColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(bookType)));
        items.getColumn("isbn").setPreferredWidth(100);
        items.getColumn("titolo").setPreferredWidth(280);
        items.getColumn("editore").setPreferredWidth(100);
        items.getColumn("modalità fruizione").setPreferredWidth(110);
        items.getColumn("anno pubblicazione").setPreferredWidth(120);
        items.getColumn("descrizione").setPreferredWidth(175);
        items.getColumn("genere").setPreferredWidth(70);
        items.getColumn("target").setPreferredWidth(60);
        items.getColumn("materia").setPreferredWidth(80);
        items.getColumn("tipo").setPreferredWidth(80);
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("isbn");
            tableContent[i][1] = rowData.get("title");
            tableContent[i][2] = rowData.get("publisher");
            tableContent[i][3] = ((Book.FruitionMode) rowData.get("fruition_mode")).name().toLowerCase();
            tableContent[i][4] = rowData.get("publication_year");
            tableContent[i][5] = rowData.get("cover");
            tableContent[i][6] = rowData.get("description");
            tableContent[i][7] = rowData.get("genre");
            tableContent[i][8] = rowData.get("target");
            tableContent[i][9] = rowData.get("topic");
            tableContent[i][10] = ((Book.BookType) rowData.get("book_type")).name().toLowerCase();
        }

        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    protected boolean onRemoveButton(Object id) throws Exception {
        return appController.removeBookFromDatabase((String) id);
    }

    @Override
    protected Object onSaveButton(ArrayList<String> data) throws Exception {
        return appController.updateBookFromDatabase(data);
    }

    @Override
    protected void onViewButton(Object id) {

    }
}
