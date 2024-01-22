package GUI.Components;

import GUI.AdminPageGUI;
import GUI.AppView;
import Model.ScientificPublication;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScientificPublicationsCrudTable extends CrudTable{
    public ScientificPublicationsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, false, true, true, true, "Aggiungi un articolo scientifico", "Modifica un articolo scientifico");
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        String[] fruitionModes = {"digitale", "cartaceo", "audiolibro"};
        TableColumn fruitionModeColumn = items.getColumn("modalità fruizione");
        fruitionModeColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(fruitionModes)));

        items.getColumn("doi").setMinWidth(180);
        items.getColumn("titolo").setMinWidth(320);
        items.getColumn("editore").setMinWidth(180);
        items.getColumn("modalità fruizione").setMinWidth(120);
        items.getColumn("anno pubblicazione").setMinWidth(120);
        items.getColumn("descrizione").setMinWidth(300);

        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            //System.out.println(formData);
            try {
                Map<String, String> formData = this.createView.getFormData();
                parentView.getAppController().insertPublicationIntoDatabase(formData.get("Doi"), formData.get("Titolo"), formData.get("Editore"), formData.get("Modalità di fruizione"), Integer.parseInt(formData.get("Anno di pubblicazione")), null, formData.get("Descrizione"));
            } catch (Exception exception){
                JOptionPane.showMessageDialog(parentView.getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
            //parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), parentView.getAppController().getRenderedBooks()));
        });
    }

    @Override
    protected DefaultTableModel getModel() {
        Object[][] tableContent = new Object[data.size()][columns.length];

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> rowData = data.get(i);
            tableContent[i][0] = rowData.get("doi");
            tableContent[i][1] = rowData.get("title");
            tableContent[i][2] = rowData.get("publisher");
            tableContent[i][3] = ((ScientificPublication.FruitionMode) rowData.get("fruition_mode")).name().toLowerCase();
            tableContent[i][4] = rowData.get("publication_year");
            tableContent[i][5] = rowData.get("cover");
            tableContent[i][6] = rowData.get("description");
        }

        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeScientificPublicationFromDatabase((String) id);
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
        String[] fruitionModes = {"digitale", "cartaceo", "audiolibro"};
        schema.put("Doi", new JTextField());
        schema.put("Titolo", new JTextField());
        schema.put("Editore", new JTextField());
        schema.put("Modalità di fruizione", new JComboBox<>(fruitionModes));
        schema.put("Anno di pubblicazione", new JYearChooser());
        schema.put("Descrizione", new JTextArea());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
