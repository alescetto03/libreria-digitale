package GUI.Components;

import GUI.*;
import Model.Book;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BooksCrudTable extends CrudTable{
    public BooksCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi un libro", "Modifica un libro");
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
        this.createButton.removeActionListener(createButtonListener);
        this.createButton.addActionListener((ActionEvent e) -> {
            parentView.getAppController().switchView(new InsertBookGUI(parentView.getAppController()));
        });

        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            try {
                Map<String, String> formData = this.createView.getFormData();
                parentView.getAppController().insertBookIntoDatabase(formData.get("Isbn"), formData.get("Titolo"), formData.get("Editore"), formData.get("Modalità di fruizione"), Integer.parseInt(formData.get("Anno di pubblicazione")), null, formData.get("Descrizione"), formData.get("Genere"), formData.get("Tipo"), formData.get("Target"), formData.get("Materia"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new BooksCrudTable(parentView, "Libri:", new String[]{"isbn", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione", "genere", "target", "materia", "tipo"}, parentView.getAppController().getRenderedBooks())));
                JOptionPane.showMessageDialog(parentView.getAppController().getCurrentView().getContentPane(), "Libro inserito con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception){
                JOptionPane.showMessageDialog(parentView.getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });
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
    protected void onUpdateButton(Object id, ArrayList<String> data) {
        if (data.get(10).equals("romanzo")) {
            parentView.getAppController().switchView(new UpdateNovelBook(parentView.getAppController(), data, (String) id));
        } else {
            parentView.getAppController().switchView(new UpdateEducationalBook(parentView.getAppController(), data, (String) id));
        }
    }

    @Override
    protected void onViewButton(Object id) { parentView.getAppController().showAuthorsOfBook((String) id); }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        String[] types = {"romanzo", "didattico"};
        String[] fruitionModes = {"digitale", "cartaceo", "audiolibro"};
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
        Map<String, JComponent> schema = new HashMap<>();
        String[] types = {"romanzo", "didattico"};
        String[] fruitionModes = {"romanzo", "didattico"};
        schema.put("Isbn", new JTextField(data.get(0)));
        schema.put("Titolo", new JTextField(data.get(1)));
        schema.put("Editore", new JTextField(data.get(2)));
        JComboBox<String> fruitionField = new JComboBox<>(fruitionModes);
        fruitionField.setSelectedItem(data.get(3));
        schema.put("Modalità di fruizione", fruitionField);
        JYearChooser yearField = new JYearChooser();
        yearField.setYear(Integer.parseInt(data.get(4)));
        schema.put("Anno di pubblicazione", yearField);

        schema.put("Descrizione", new JTextArea(data.get(6)));
        schema.put("Genere", new JTextField(data.get(7)));
        schema.put("Target", new JTextField(data.get(8)));
        schema.put("Materia", new JTextField(data.get(9)));
        JComboBox<String> typeField = new JComboBox<>(types);
        typeField.setSelectedItem(data.get(10));
        schema.put("Tipo", typeField);

        return schema;
    }
}
