package GUI.Components;

import GUI.AppView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditorialCollectionsCrudTable extends CrudTable {
    public EditorialCollectionsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, false, true, true, true, "Aggiungi una collana", "Modifica una collana");



        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            //System.out.println(formData);
            try {
                Map<String, String> formData = this.createView.getFormData();
                parentView.getAppController().insertEditorialCollectionIntoDatabase(formData.get("Issn"), formData.get("Nome"), formData.get("Editore"));
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
    protected Object onUpdateButton(ArrayList<String> data) {
        return null;
    }

    @Override
    protected void onViewButton(Object id) {
    }

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
        return null;
    }
}
