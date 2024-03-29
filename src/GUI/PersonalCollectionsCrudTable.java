package GUI;

import GUI.*;
import Model.Collection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CRUD Table che gestisce le raccolte personali di un utente
 */
public class PersonalCollectionsCrudTable extends CrudTable {
    public PersonalCollectionsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi una raccolta", "Modifica una raccolta");

        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            try {
                Map<String, String> formData = this.createView.getFormData();
                parentView.getAppController().insertPersonalCollectionIntoDatabase(formData.get("Nome"), formData.get("Visibilità"));
                parentView.getAppController().showHomepage();
                JOptionPane.showMessageDialog(parentView.getAppController().getCurrentView().getContentPane(), "Hai inserito una nuova raccolta!", "Congratulazioni!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception){
                JOptionPane.showMessageDialog(parentView.getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });


        TableColumn idColumn = items.getColumn("id");
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        String[] visibilities = {"pubblica", "privata"};
        TableColumn visibilityColumn = items.getColumn("visibilita");
        visibilityColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(visibilities)));
        for (int i = 0; i < items.getRowCount(); i++) {
            items.getValueAt(i, visibilityColumn.getModelIndex());
        }
    }
    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("id");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = ((Collection.Visibility) rowData.get("visibility")).name().toLowerCase();
        }
        return new DefaultTableModel(tableContent, columns);
    }
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        String[] visibilities = {"pubblica", "privata"};
        schema.put("Nome", new JTextField());
        schema.put("Visibilità", new JComboBox<>(visibilities));
        return schema;
    }
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        Map<String, JComponent> schema = new HashMap<>();
        String[] visibilities = {"pubblica", "privata"};
        JTextField nameField = new JTextField();
        JComboBox<String> visibilityField = new JComboBox<>(visibilities);
        nameField.setText(data.get(1));
        visibilityField.setSelectedItem(data.get(2));
        schema.put("Nome", nameField);
        schema.put("Visibilità", visibilityField);
        return schema;
    }

    @Override
    public boolean onRemoveButton(Object id) {
        boolean isDeleted = parentView.getAppController().removeCollectionFromDatabase((Integer) id);
        if (isDeleted) {
            parentView.getAppController().showHomepage();
        }
        return isDeleted;
    }

    @Override
    protected void onUpdateButton(Object id, ArrayList<String> data) {
        this.updateView = new ModelManipulationFormGUI(this.parentView.getAppController(), this.parentView, this.getFormSchema(data), this.updateViewTitle);
        this.parentView.getAppController().switchView(this.updateView);
        this.updateView.getConfirmButton().addActionListener((ActionEvent e) -> {
            Map<String, String> formData = updateView.getFormData();
            try {
                parentView.getAppController().updatePersonalCollectionIntoDatabase(Integer.parseInt(data.get(0)), formData.get("Nome"), formData.get("Visibilità"));
                parentView.getAppController().showHomepage();
                JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentWindow().getContentPane(), "Hai modificato un raccolta!", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this.updateView.getContentPane(), exception.getMessage(), "Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    protected void onViewButton(Object id) {
        parentView.getAppController().showCollectionItems(id);
    }
}