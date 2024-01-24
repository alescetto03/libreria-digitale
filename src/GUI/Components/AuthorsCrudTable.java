package GUI.Components;

import GUI.AdminPageGUI;
import GUI.AppView;
import GUI.ModelManipulationFormGUI;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
            try {
                Map<String, String> formData = this.createView.getFormData();
                LocalDate parsedBirthdate = formData.get("Data di nascita") != null ? LocalDate.parse(formData.get("Data di nascita")) : null;
                LocalDate parseDeathDate = formData.get("Data di morte") != null ? LocalDate.parse(formData.get("Data di morte")) : null;
                parentView.getAppController().insertAuthorIntoDatabase(formData.get("Nome"), parsedBirthdate, parseDeathDate, formData.get("Nazionalità"), formData.get("Biografia"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new AuthorsCrudTable(parentView, "Autori:", new String[]{"id", "nome", "data di nascita", "data di morte", "nazionalità", "biografia"}, parentView.getAppController().getRenderedAuthors())));
                JOptionPane.showMessageDialog(parentView.getContentPane(), "Inserimento effettuato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch(Exception exception){
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
    protected void onUpdateButton(Object id, ArrayList<String> data) {
        this.updateView = new ModelManipulationFormGUI(this.parentView.getAppController(), this.parentView, this.getFormSchema(data), this.updateViewTitle);
        this.parentView.getAppController().switchView(this.updateView);
        Map<String, String> formData = updateView.getFormData();
        this.updateView.getConfirmButton().addActionListener((ActionEvent e) -> {
            try {
                LocalDate parsedBirthdate = formData.get("Data di nascita") != null ? LocalDate.parse(formData.get("Data di nascita")) : null;
                LocalDate parseDeathDate = formData.get("Data di morte") != null ? LocalDate.parse(formData.get("Data di morte")) : null;
                Map<String, Object> renderedData = parentView.getAppController().updateAuthorFromDatabase(Integer.parseInt(data.get(0)), formData.get("Nome"), parsedBirthdate, parseDeathDate, formData.get("Nazionalità"), formData.get("Biografia"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new AuthorsCrudTable(parentView, "Autori:", new String[]{"id", "nome", "data di nascita", "data di morte", "nazionalità", "biografia"}, parentView.getAppController().getRenderedAuthors())));
                JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentWindow().getContentPane(), "L'autore \"" + renderedData.get("name") + "\" è stato modificato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });
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
        Map<String, JComponent> schema = new HashMap<>();
        JTextField nameField = new JTextField();
        JDateChooser birthDateField = new JDateChooser();
        JDateChooser deathDateField = new JDateChooser();
        JTextField nationalityField = new JTextField();
        JTextArea biographyField = new JTextArea();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        nameField.setText(data.get(1));
        try {
            Date formattedBirthDateField = formatter.parse(data.get((2)));
            Date formattedDeathDateField = formatter.parse(data.get((3)));
            birthDateField.setDate(formattedBirthDateField);
            deathDateField.setDate(formattedDeathDateField);
        } catch (ParseException exception) {
            System.out.println(exception.getMessage());
        }
        nationalityField.setText(data.get(4));
        biographyField.setText(data.get(5));

        schema.put("Nome", nameField);
        schema.put("Data di nascita", birthDateField);
        schema.put("Data di morte", deathDateField);
        schema.put("Nazionalità", nationalityField);
        schema.put("Biografia", biographyField);
        return schema;
    }
}
