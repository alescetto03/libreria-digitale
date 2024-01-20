package GUI.Components;

import GUI.AppView;
import Model.Book;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BooksCrudTable extends CrudTable{
    public BooksCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, false, true, true, true, "Aggiungi un libro", "Modifica un libro");
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn fruitionModeColumn = items.getColumn("modalità fruizione");
        TableColumn bookTypeColumn = items.getColumn("tipo");

        String[] fruitionModes = {"cartaceo", "digitale", "audiolibro"};
        String[] bookTypes = {"romanzo", "didattico"};
        fruitionModeColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(fruitionModes)));
        bookTypeColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(bookTypes)));
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
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeBookFromDatabase((String) id);
    }

    @Override
    protected Object onUpdateButton(ArrayList<String> data) {
        //return parentView.getAppController().updateBookFromDatabase(this.data);
        return null;
    }

    @Override
    protected void onViewButton(Object id) {

    }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        String[] types = {"romanzo", "didattico"};
        String[] fruitionModes = {"romanzo", "didattico"};
        schema.put("Isbn", new JTextField());
        schema.put("Titolo", new JTextField());
        schema.put("Editore", new JTextField());
        schema.put("Modalità di fruizione", new JComboBox<>(fruitionModes));
        schema.put("Anno di pubblicazione", new JYearChooser());
        schema.put("Descrizione", new JTextArea());
        schema.put("Genere", new JTextField());
        schema.put("Target", new JTextField());
        schema.put("Materia", new JTextField());
        schema.put("Tipo", new JComboBox<>(types));
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
