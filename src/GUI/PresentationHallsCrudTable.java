package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CRUD Table che gestisce le raccolte personali di un utente
 */
public class PresentationHallsCrudTable extends CrudTable {
    public PresentationHallsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi una sala/libreria", "Modifica una sala/libreria");
        items.getColumn("id").setMaxWidth(50);
        items.getColumn("id").setMinWidth(50);
        items.getColumn("azioni").setMaxWidth(100);
        items.getColumn("azioni").setMinWidth(100);

        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            try {
                Map<String, String> formData = this.createView.getFormData();
                parentView.getAppController().insertPresentationHallIntoDatabase(formData.get("Nome"), formData.get("Indirizzo"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new PresentationHallsCrudTable(parentView, "Librerie:", new String[]{"id", "nome", "indirizzo"}, parentView.getAppController().getRenderedPresentationHalls())));
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
            tableContent[i][0] = rowData.get("id");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("address");
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
        if (parentView.getAppController().removePresentationHallFromDatabase((Integer) id)){
            parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new PresentationHallsCrudTable(parentView, "Librerie:", new String[]{"id", "nome", "indirizzo"}, parentView.getAppController().getRenderedPresentationHalls())));
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
                Map<String, Object> renderedData = parentView.getAppController().updatePresentationHallFromDatabase(Integer.parseInt(data.get(0)), formData.get("Nome"), formData.get("Indirizzo"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new PresentationHallsCrudTable(parentView, "Librerie:", new String[]{"id", "nome", "indirizzo"}, parentView.getAppController().getRenderedPresentationHalls())));
                JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentWindow().getContentPane(), "La collana \"" + renderedData.get("name") + " - " + renderedData.get("address") + "\" è stata modificata con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(parentView.getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    protected void onViewButton(Object id) {
        parentView.getAppController().showBookPresentations(Integer.parseInt((String) id));
    }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Nome", new JTextField());
        schema.put("Indirizzo", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Nome", new JTextField(data.get(1)));
        schema.put("Indirizzo", new JTextField(data.get(2)));
        return schema;
    }
}
