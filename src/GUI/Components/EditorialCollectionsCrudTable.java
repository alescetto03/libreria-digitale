package GUI.Components;

import GUI.AdminPageGUI;
import GUI.AppView;
import GUI.ModelManipulationFormGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditorialCollectionsCrudTable extends CrudTable {
    public EditorialCollectionsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi una collana", "Modifica una collana");
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("issn");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("publisher");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeEditorialCollectionFromDatabase((String) id);
    }

    @Override
    protected void onUpdateButton(Object id, ArrayList<String> data) {
        this.updateView = new ModelManipulationFormGUI(this.parentView.getAppController(), this.parentView, this.getFormSchema(data), this.updateViewTitle);
        this.parentView.getAppController().switchView(this.updateView);
        this.updateView.getConfirmButton().addActionListener((ActionEvent e) -> {
            Map<String, String> formData = updateView.getFormData();
            parentView.getAppController().updateEditorialCollection(data.get(0), formData.get("Issn"), formData.get("Nome"), formData.get("Editore"));
            parentView.getAppController().showHomepage();
        });
    }

    @Override
    protected void onViewButton(Object id) { parentView.getAppController().showBooksInEditorialCollection((String) id); }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Issn", new JTextField());
        schema.put("Nome", new JTextField());
        schema.put("Editore", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        Map<String, JComponent> schema = new HashMap<>();
        JTextField issnField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField publisher = new JTextField();

        issnField.setText(data.get(0));
        nameField.setText(data.get(1));
        publisher.setText(data.get(2));

        schema.put("Issn", issnField);
        schema.put("Nome", nameField);
        schema.put("Editore", publisher);
        return schema;
    }
}
