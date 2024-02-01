package GUI.Components;

import GUI.AdminPageGUI;
import GUI.AppView;
import GUI.ModelManipulationFormGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditorialCollectionsCrudTable extends CrudTable {
    public EditorialCollectionsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi una collana", "Modifica una collana");
        items.getColumn("azioni").setMinWidth(100);
        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            try {
                Map<String, String> formData = this.createView.getFormData();
                parentView.getAppController().insertEditorialCollectionIntoDatabase(formData.get("Issn"), formData.get("Nome"), formData.get("Editore"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new EditorialCollectionsCrudTable(parentView, "Collane:", new String[]{"issn", "nome", "editore"}, parentView.getAppController().getRenderedEditorialCollections())));
                JOptionPane.showMessageDialog(parentView.getContentPane(), "Inserimento effettuato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
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
        return new DefaultTableModel(tableContent, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column != columns.length - 1);
            }
        };
    }

    @Override
    public boolean onRemoveButton(Object id) {
        if (parentView.getAppController().removeEditorialCollectionFromDatabase((String) id)){
            parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new EditorialCollectionsCrudTable(parentView, "Collane:", new String[]{"issn", "nome", "editore"}, parentView.getAppController().getRenderedEditorialCollections())));
            JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentWindow().getContentPane(), "Rimozione avvenuta con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);

            return true;
        }
        return false;
    }

    @Override
    protected void onUpdateButton(Object id, ArrayList<String> data) {
        this.updateView = new ModelManipulationFormGUI(this.parentView.getAppController(), this.parentView, this.getFormSchema(data), this.updateViewTitle);
        this.parentView.getAppController().switchView(this.updateView);
        this.updateView.getConfirmButton().addActionListener((ActionEvent e) -> {
            Map<String, String> formData = updateView.getFormData();
            try {
                Map<String, Object> renderedData = parentView.getAppController().updateEditorialCollectionFromDatabase(data.get(0), formData.get("Issn"), formData.get("Nome"), formData.get("Editore"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new EditorialCollectionsCrudTable(parentView, "Collane:", new String[]{"issn", "nome", "editore"}, parentView.getAppController().getRenderedEditorialCollections())));
                JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentWindow().getContentPane(), "La collana \"" + renderedData.get("issn") + " - " + renderedData.get("name") + "\" è stata modificata con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(parentView.getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
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
