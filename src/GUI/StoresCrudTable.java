package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CRUD Table che gestisce tutti i negozi presenti nel database dell'applicativo
 */
public class StoresCrudTable extends CrudTable {
    public StoresCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi un negozio", "Modifica un negozio");
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        items.getColumn("partita iva").setMinWidth(90);
        items.getColumn("partita iva").setMaxWidth(90);
        items.getColumn("nome").setMinWidth(150);
        items.getColumn("indirizzo").setMinWidth(180);
        items.getColumn("url").setMinWidth(200);
        items.getColumn("azioni").setMinWidth(100);

        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            try {
                Map<String, String> formData = this.createView.getFormData();
                parentView.getAppController().insertStoreIntoDatabase(formData.get("Partita Iva"), formData.get("Nome"), formData.get("Indirizzo"), formData.get("Url"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new StoresCrudTable(parentView, "Negozi:", new String[]{"partita iva", "nome", "indirizzo", "url"}, parentView.getAppController().getRenderedStores())));
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
            tableContent[i][0] = rowData.get("partita_iva");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("address");
            tableContent[i][3] = rowData.get("url");
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
        if (parentView.getAppController().removeStoreFromDatabase((String) id)){
            parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new StoresCrudTable(parentView, "Negozi:", new String[]{"partita iva", "nome", "indirizzo", "url"}, parentView.getAppController().getRenderedStores())));
            JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentView().getContentPane(), "Rimozione avvenuta con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);

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
                Map<String, Object> renderedData = parentView.getAppController().updateStoreFromDatabase(data.get(0), formData.get("Partita Iva"), formData.get("Nome"), formData.get("Indirizzo"), formData.get("Url"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new StoresCrudTable(parentView, "Negozi:", new String[]{"partita iva", "nome", "indirizzo", "url"}, parentView.getAppController().getRenderedStores())));
                JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentView().getContentPane(), "Il negozio " + renderedData.get("partita_iva") + " - " + renderedData.get("name") + " è stato modificato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this.updateView.getContentPane(), exception.getMessage(), "Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    protected void onViewButton(Object id) { parentView.getAppController().showBookSales((String) id); }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Partita Iva", new JTextField());
        schema.put("Nome", new JTextField());
        schema.put("Indirizzo", new JTextField());
        schema.put("Url", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        Map<String, JComponent> schema = new HashMap<>();
        JTextField partitaIvaField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField urlField = new JTextField();

        partitaIvaField.setText(data.get(0));
        nameField.setText(data.get(1));
        addressField.setText(data.get(2));
        urlField.setText(data.get(3));

        schema.put("Partita Iva", partitaIvaField);
        schema.put("Nome", nameField);
        schema.put("Indirizzo", addressField);
        schema.put("Url", urlField);
        return schema;
    }
}
