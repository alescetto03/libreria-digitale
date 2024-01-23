package GUI.Components;

import GUI.AdminPageGUI;
import GUI.AppView;
import GUI.ModelManipulationFormGUI;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.*;

public class AuthorsCrudTable extends CrudTable {
    public AuthorsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, false, true, true, true, "Aggiungi un autore", "Modifica un autore");
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        items.getColumn("id").setMaxWidth(50);
        items.getColumn("nome").setMinWidth(100);
        items.getColumn("data di nascita").setMinWidth(110);
        items.getColumn("data di morte").setMinWidth(110);
        items.getColumn("nazionalità").setMinWidth(100);
        items.getColumn("biografia").setMinWidth(300);
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("id");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("birth_date");
            tableContent[i][3] = rowData.get("death_date");
            tableContent[i][4] = rowData.get("nationality");
            tableContent[i][5] = rowData.get("bio");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeAuthorFromDatabase(Integer.parseInt((String) id));
    }

    @Override
    protected void onUpdateButton(Object id, ArrayList<String> data) {
        this.updateView = new ModelManipulationFormGUI(this.parentView.getAppController(), this.parentView, this.getFormSchema(data), this.updateViewTitle);
        this.parentView.getAppController().switchView(this.updateView);
        this.updateView.getConfirmButton().addActionListener((ActionEvent e) -> {
            Map<String, String> formData = updateView.getFormData();
            parentView.getAppController().updatePersonalCollectionIntoDatabase(Integer.parseInt(data.get(0)), formData.get("Nome"), formData.get("Visibilità"));
            parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), this));
        });
    }

    @Override
    protected void onViewButton(Object id) {

    }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Nome", new JTextField());
        schema.put("Data di nascita", new JDateChooser());
        schema.put("Data di morte", new JDateChooser());
        schema.put("Nazionalità", new JTextField());
        schema.put("Biografia", new JTextArea());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        Map<String, JComponent> schema = new HashMap<>();
        JTextField nameField = new JTextField();
        JDateChooser birthDateField = new JDateChooser();
        JDateChooser deathDateField = new JDateChooser();
        JTextField nationalityField = new JTextField();
        JTextArea biographyField = new JTextArea();

        nameField.setText(data.get(1));
        birthDateField.setDateFormatString(data.get(2));
        deathDateField.setDateFormatString(data.get(3));
        nationalityField.setText(data.get(4));
        biographyField.setText(data.get(5));

        schema.put("Nome", nameField);
        schema.put("Data di nascita", birthDateField);
        schema.put("Data di morte", deathDateField);
        schema.put("Nazionalità", nationalityField);
        schema.put("Biografia", biographyField);
        return schema;
    }
}
