package GUI;

import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JournalsCrudTable extends CrudTable {
    public JournalsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi una rivista", "Modifica una rivista");
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        items.getColumn("issn").setMinWidth(70);
        items.getColumn("issn").setMaxWidth(70);
        items.getColumn("nome").setMinWidth(150);
        items.getColumn("argomento").setMinWidth(85);
        items.getColumn("anno di pubblicazione").setMinWidth(125);
        items.getColumn("responsabile").setMinWidth(125);
        items.getColumn("azioni").setMinWidth(100);

        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            try {
                Map<String, String> formData = this.createView.getFormData();
                parentView.getAppController().insertJournalsIntoDatabase(formData.get("Issn"), formData.get("Nome"), formData.get("Argomento"), Integer.parseInt(formData.get("Anno di pubblicazione")), formData.get("Responsabile"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new JournalsCrudTable(parentView, "Riviste:", new String[]{"issn", "nome", "argomento", "anno di pubblicazione", "responsabile"}, parentView.getAppController().getRenderedJournals())));
                JOptionPane.showMessageDialog(parentView.getContentPane(), "Inserimento effettuato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
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
            tableContent[i][0] = rowData.get("issn");
            tableContent[i][1] = rowData.get("name");
            tableContent[i][2] = rowData.get("argument");
            tableContent[i][3] = rowData.get("publication_year");
            tableContent[i][4] = rowData.get("manager");
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
        if (parentView.getAppController().removeJournalFromDatabase((String) id)){
            parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new JournalsCrudTable(parentView, "Riviste:", new String[]{"issn", "nome", "argomento", "anno di pubblicazione", "responsabile"}, parentView.getAppController().getRenderedJournals())));
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
                Map<String, Object> renderedData = parentView.getAppController().updateJournalFromDatabase(data.get(0), formData.get("Issn"), formData.get("Nome"), formData.get("Argomento"), Integer.parseInt(formData.get("Anno di pubblicazione")), formData.get("Responsabile"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new JournalsCrudTable(parentView, "Riviste:", new String[]{"issn", "nome", "argomento", "anno di pubblicazione", "responsabile"}, parentView.getAppController().getRenderedJournals())));
                JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentWindow().getContentPane(), "La rivista \"" + renderedData.get("issn") + " - " + renderedData.get("name") + "\" è stata modificata con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(parentView.getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    protected void onViewButton(Object id) { parentView.getAppController().showScientificPublicationsInJournal((String) id); }

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();
        schema.put("Issn", new JTextField());
        schema.put("Nome", new JTextField());
        schema.put("Argomento", new JTextField());
        schema.put("Anno di pubblicazione", new JYearChooser());
        schema.put("Responsabile", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        Map<String, JComponent> schema = new HashMap<>();
        JTextField issnField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField argumentField = new JTextField();
        JYearChooser publicationYearField = new JYearChooser();
        JTextField managerField = new JTextField();

        issnField.setText(data.get(0));
        nameField.setText(data.get(1));
        argumentField.setText(data.get(2));
        publicationYearField.setYear(Integer.parseInt(data.get(3)));
        managerField.setText(data.get(4));

        schema.put("Issn", issnField);
        schema.put("Nome", nameField);
        schema.put("Argomento", argumentField);
        schema.put("Anno di pubblicazione", publicationYearField);
        schema.put("Responsabile", managerField);
        return schema;
    }
}
