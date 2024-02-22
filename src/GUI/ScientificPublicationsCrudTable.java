package GUI;

import Model.ScientificPublication;
import com.toedter.calendar.JYearChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CRUD Table che gestisce tutti gli articoli scientifici presenti nel database dell'applicativo
 */
public class ScientificPublicationsCrudTable extends CrudTable {
    public ScientificPublicationsCrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data) {
        super(parentView, title, columns, data, true, true, true, true, "Aggiungi un articolo scientifico", "Aggiorna un articolo scientifico");
        items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        items.getColumn("doi").setMinWidth(180);
        items.getColumn("titolo").setMinWidth(320);
        items.getColumn("editore").setMinWidth(180);
        items.getColumn("modalità fruizione").setMinWidth(120);
        items.getColumn("anno pubblicazione").setMinWidth(120);
        items.getColumn("descrizione").setMinWidth(300);
        items.getColumn("azioni").setMinWidth(100);



        this.createView.getConfirmButton().addActionListener((ActionEvent e) -> {
            Map<String, String> formData = this.createView.getFormData();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            String extensionFile = null;
            if (scientificPublicationCover != null) {
                String fullName = scientificPublicationCover.getName();
                extensionFile = fullName.substring(fullName.lastIndexOf('.') + 1);
            }
            try {
                if (scientificPublicationCover != null) {
                    ImageIO.write(ImageIO.read(scientificPublicationCover), extensionFile, stream);
                    byte[] cover = stream.toByteArray();
                    parentView.getAppController().insertPublicationIntoDatabase(formData.get("Doi"), formData.get("Titolo"), formData.get("Editore"), formData.get("Modalità di fruizione"), Integer.parseInt(formData.get("Anno di pubblicazione")), cover, formData.get("Descrizione"));
                    parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new AuthorsCrudTable(parentView, "Autori:", new String[]{"id", "nome", "data di nascita", "data di morte", "nazionalità", "biografia"}, parentView.getAppController().getRenderedAuthors())));
                    JOptionPane.showMessageDialog(parentView.getContentPane(), "Inserimento effettuato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    parentView.getAppController().insertPublicationIntoDatabase(formData.get("Doi"), formData.get("Titolo"), formData.get("Editore"), formData.get("Modalità di fruizione"), Integer.parseInt(formData.get("Anno di pubblicazione")), null, formData.get("Descrizione"));
                    parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new AuthorsCrudTable(parentView, "Autori:", new String[]{"id", "nome", "data di nascita", "data di morte", "nazionalità", "biografia"}, parentView.getAppController().getRenderedAuthors())));
                    JOptionPane.showMessageDialog(parentView.getContentPane(), "Inserimento effettuato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                }
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
            tableContent[i][0] = rowData.get("doi");
            tableContent[i][1] = rowData.get("title");
            tableContent[i][2] = rowData.get("publisher");
            tableContent[i][3] = ((ScientificPublication.FruitionMode) rowData.get("fruition_mode")).name().toLowerCase();
            tableContent[i][4] = rowData.get("publication_year");
            tableContent[i][5] = rowData.get("cover");
            tableContent[i][6] = rowData.get("description");
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
        if (parentView.getAppController().removeScientificPublicationFromDatabase((String) id)){
            parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new ScientificPublicationsCrudTable(parentView, "Articoli scientifici:", new String[]{"doi", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione"}, parentView.getAppController().getRenderedScientificPublications())));
            JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentWindow().getContentPane(), "Rimozione avvenuta con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    @Override
    protected void onUpdateButton(Object id, ArrayList<String> data) {
        this.updateView = new ModelManipulationFormGUI(parentView.getAppController(), parentView, getFormSchema(data), "Aggiorna un articolo scientifico");
        this.parentView.getAppController().switchView(this.updateView);
        this.updateView.getConfirmButton().addActionListener((ActionEvent e) -> {
            Map<String, String> formData = updateView.getFormData();
            try {
                Map<String, Object> renderedData = parentView.getAppController().updateScientificPublicationFromDatabase((String) id, formData.get("Doi"), formData.get("Titolo"), formData.get("Editore"), formData.get("Modalità di fruizione"), Integer.parseInt(formData.get("Anno di pubblicazione")), formData.get("Descrizione"));
                parentView.getAppController().switchView(new AdminPageGUI(parentView.getAppController(), new ScientificPublicationsCrudTable(parentView, "Articoli scientifici:", new String[]{"doi", "titolo", "editore", "modalità fruizione", "anno pubblicazione", "copertina", "descrizione"}, parentView.getAppController().getRenderedScientificPublications())));
                JOptionPane.showMessageDialog(this.parentView.getAppController().getCurrentWindow().getContentPane(), "L'articolo " + renderedData.get("doi") + " - " + renderedData.get("title") + " è stato modificato con successo", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this.updateView.getContentPane(), exception.getMessage(), "Errore!!!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    protected void onViewButton(Object id) { parentView.getAppController().showAuthorsOfScientificPublication((String) id); }

    public File scientificPublicationCover = null;
    private static JFileChooser fileChooser = new JFileChooser();

    static {
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tutte le immagini", "jpg", "jpeg", "png"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Immagine JPEG", "jpg", "jpeg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Immagine PNG", "png"));
    };

    @Override
    protected Map<String, JComponent> getFormSchema() {
        Map<String, JComponent> schema = new HashMap<>();

        JTextArea descriptionField = new JTextArea();
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);

        String[] fruitionModes = {"digitale", "cartaceo", "audiolibro"};

        JButton coverButton = new JButton("Scegli una copertina.");

        coverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int returnValue = fileChooser.showOpenDialog(parentView.getContentPane());

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    scientificPublicationCover = fileChooser.getSelectedFile();
                }
            }
        });

        schema.put("Doi", new JTextField());
        schema.put("Titolo", new JTextField());
        schema.put("Editore", new JTextField());
        schema.put("Modalità di fruizione", new JComboBox<>(fruitionModes));
        schema.put("Anno di pubblicazione", new JYearChooser());
        schema.put("Descrizione", descriptionField);
        schema.put("Copertina", coverButton);
        return schema;
    }

    @Override
    protected Map<String, JComponent> getFormSchema(ArrayList<String> data) {
        Map<String, JComponent> schema = new HashMap<>();
        JTextField doiField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField publisherField = new JTextField();
        String[] fruitionModes = {"digitale", "cartaceo", "audiolibro"};
        JComboBox<String> fruitionModeField = new JComboBox<>(fruitionModes);
        JYearChooser publicationYearField = new JYearChooser();
        JTextArea descriptionField = new JTextArea();
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);

        doiField.setText(data.get(0));
        titleField.setText(data.get(1));
        publisherField.setText(data.get(2));
        fruitionModeField.setSelectedItem(data.get(3));
        publicationYearField.setYear(Integer.parseInt(data.get(4)));
        descriptionField.setText(data.get(6));

        schema.put("Doi", doiField);
        schema.put("Titolo", titleField);
        schema.put("Editore", publisherField);
        schema.put("Modalità di fruizione", fruitionModeField);
        schema.put("Anno di pubblicazione", publicationYearField);
        schema.put("Descrizione", descriptionField);
        return schema;
    }
}
