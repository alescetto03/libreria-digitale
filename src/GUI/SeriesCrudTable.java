package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CRUD Table che gestisce tutte le serie presenti nel database dell'applicativo
 */
public class SeriesCrudTable extends CrudTable {
    public SeriesCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, false, false, true, true, "Aggiungi un libro in una serie", "Modifica un libro in una serie");
        items.getColumn("prequel").setMaxWidth(100);
        items.getColumn("prequel").setMinWidth(100);
        items.getColumn("sequel").setMaxWidth(100);
        items.getColumn("sequel").setMinWidth(100);
        items.getColumn("azioni").setMaxWidth(60);
        items.getColumn("azioni").setMinWidth(60);


        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            //System.out.println(formData);
            try {
                Map<String, String> formData = this.createView.getFormData();
                parentView.getAppController().insertSeriesIntoDatabase(formData.get("Prequel"), formData.get("Sequel"), formData.get("Nome"));
            }catch (Exception exception){
                JOptionPane.showMessageDialog(parentView.getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("prequel");
            tableContent[i][1] = rowData.get("sequel");
            tableContent[i][2] = rowData.get("name");
        }
        return new DefaultTableModel(tableContent, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column != columns.length - 1);
            }
        };
    }

    public boolean onRemoveButton(Object id) {
        if (parentView.getAppController().removeSerieFromDatabase((String) id)){
            parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new SeriesCrudTable(parentView, "Serie:", new String[]{"prequel", "sequel", "nome"}, parentView.getAppController().getRenderedSeries())));
            JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentWindow().getContentPane(), "Rimozione avvenuta con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    @Override
    protected void onUpdateButton(Object id, ArrayList<String> data) {
    }

    @Override
    protected void onViewButton(Object id) {

    }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Nome", new JTextField());
        schema.put("Prequel", new JTextField());
        schema.put("Sequel", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
