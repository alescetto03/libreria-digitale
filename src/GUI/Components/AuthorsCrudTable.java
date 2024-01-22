package GUI.Components;

import GUI.AppView;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthorsCrudTable extends CrudTable {
    public AuthorsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, false, true, true, true, "Aggiungi un autore", "Modifica un autore");
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        items.getColumn("id").setMaxWidth(50);
        items.getColumn("nome").setMinWidth(100);
        items.getColumn("data di nascita").setMinWidth(110);
        items.getColumn("data di morte").setMinWidth(110);
        items.getColumn("nazionalità").setMinWidth(100);
        items.getColumn("biografia").setMinWidth(300);

        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            //System.out.println(formData);
            try {
                Map<String, String> formData = this.createView.getFormData();
                if (!formData.get("Data di morte").isEmpty())
                    parentView.getAppController().insertAuthorIntoDatabase(formData.get("Nome"), LocalDate.parse(formData.get("Data di nascita")), LocalDate.parse(formData.get("Data di morte")), formData.get("Nazionalità"), formData.get("Biografia"));
                else
                    parentView.getAppController().insertAuthorIntoDatabase(formData.get("Nome"), LocalDate.parse(formData.get("Data di nascita")), null, formData.get("Nazionalità"), formData.get("Biografia"));
            }catch(Exception exception){
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
            tableContent[i][2] = rowData.get("birth_date");
            tableContent[i][3] = rowData.get("death_date");
            tableContent[i][4] = rowData.get("nationality");
            tableContent[i][5] = rowData.get("bio");
        }
        return new DefaultTableModel(tableContent, columns);
    }

    @Override
    public boolean onRemoveButton(Object id) {
        return parentView.getAppController().removeAuthorFromDatabase(Integer.parseInt((String) id));
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
        schema.put("Nome", new JTextField());
        schema.put("Data di nascita", new JDateChooser());
        schema.put("Data di morte", new JDateChooser());
        schema.put("Nazionalità", new JTextField());
        schema.put("Biografia", new JTextArea());
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        return null;
    }
}
