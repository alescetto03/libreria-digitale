package GUI.Components;

import GUI.AdminPageGUI;
import GUI.AppView;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConferencesCrudTable extends CrudTable {
    public ConferencesCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, false, true, true, true, "Aggiungi una conferenza", "Modifica una conferenza");
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        items.getColumn("id").setMaxWidth(50);
        items.getColumn("luogo").setMinWidth(100);
        items.getColumn("data di inizio").setMinWidth(110);
        items.getColumn("data di fine").setMinWidth(110);
        items.getColumn("organizzatore").setMinWidth(200);
        items.getColumn("responsabile").setMinWidth(120);

        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            //System.out.println(formData);
            try {
                Map<String, String> formData = this.createView.getFormData();
                LocalDate parsedStartDate = formData.get("Data di inizio") != null ? LocalDate.parse(formData.get("Data di inizio")) : null;
                LocalDate parsedEndDate = formData.get("Data di fine") != null ? LocalDate.parse(formData.get("Data di fine")) : null;
                parentView.getAppController().insertConferenceIntoDatabase(formData.get("Luogo"), parsedStartDate, parsedEndDate, formData.get("Organizzatore"), formData.get("Responsabile"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new ConferencesCrudTable(parentView, "Conferenze:", new String[]{"id", "luogo", "data di inizio", "data di fine", "organizzatore", "responsabile"}, parentView.getAppController().getRenderedConferences())));
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
            tableContent[i][1] = rowData.get("location");
            tableContent[i][2] = rowData.get("start_date");
            tableContent[i][3] = rowData.get("end_date");
            tableContent[i][4] = rowData.get("organizer");
            tableContent[i][5] = rowData.get("manager");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeConferenceFromDatabase((Integer.parseInt((String) id)));
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
        schema.put("Luogo", new JTextField());
        schema.put("Data di inizio", new JDateChooser());
        schema.put("Data di fine", new JDateChooser());
        schema.put("Organizzatore", new JTextField());
        schema.put("Responsabile", new JTextField());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
