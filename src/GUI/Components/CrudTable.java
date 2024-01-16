package GUI.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public abstract class CrudTable extends JPanel {
    protected String[] columns;
    protected ArrayList<Map<String, Object>> data;
    protected JTable items = new JTable();
    protected abstract DefaultTableModel getModel();
    public abstract boolean onRemoveButton(int id);
    protected abstract boolean onSaveButton(ArrayList<String> data);
    private boolean displayViewButton;
    private boolean displaySaveButton;
    private boolean displayCreateButton;
    private boolean displayDeleteButton;


    public CrudTable(String title, String[] columns, ArrayList<Map<String, Object>> data, boolean displayViewButton, boolean displaySaveButton, boolean displayCreateButton, boolean displayDeleteButton) {
        this.columns = columns;
        this.data = data;
        this.displayViewButton = displayViewButton;
        this.displaySaveButton = displaySaveButton;
        this.displayCreateButton = displayCreateButton;
        this.displayDeleteButton = displayDeleteButton;

        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setLayout(new BorderLayout());
        JPanel topBar = new JPanel();
        topBar.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        topBar.add(titleLabel, BorderLayout.LINE_START);

        IconButton createButton = null;
        if (displayCreateButton) {
            createButton = new IconButton("/GUI/images/create.png", 18, 18, Image.SCALE_SMOOTH);
            topBar.add(createButton, BorderLayout.LINE_END);
        }
        add(topBar, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), 185));
        add(items, BorderLayout.CENTER);
        DefaultTableModel model = getModel();
        items.setModel(model);
        items.getTableHeader().setReorderingAllowed(false);
        items.setRowHeight(40);
        items.setRowSelectionAllowed(false);
        model.addColumn("azioni", new Object[model.getRowCount()]);
        items.getColumn("azioni").setCellRenderer(new TableActionsPanelRenderer(this,displayViewButton, displaySaveButton, displayDeleteButton));
        items.getColumn("azioni").setCellEditor(new TableActionsPanelEditor(this, displayViewButton, displaySaveButton, displayDeleteButton));
        scrollPane.setViewportView(items);
        add(scrollPane);

        if (createButton != null) {
            createButton.addActionListener((ActionEvent e) -> {
                int columnCount = items.getColumnCount();
                String[] emptyRow = new String[columnCount];
                Arrays.fill(emptyRow, "");
                model.insertRow(0, emptyRow);
            });
        }
    }

    private void createTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), 185));
        add(items, BorderLayout.CENTER);
        DefaultTableModel model = getModel();
        items.setModel(model);
        items.getTableHeader().setReorderingAllowed(false);
        items.setRowHeight(40);
        items.setRowSelectionAllowed(false);
        model.addColumn("azioni", new Object[model.getRowCount()]);
        items.getColumn("azioni").setCellRenderer(new TableActionsPanelRenderer(this,displayViewButton, displaySaveButton, displayDeleteButton));
        items.getColumn("azioni").setCellEditor(new TableActionsPanelEditor(this, displayViewButton, displaySaveButton, displayDeleteButton));
        scrollPane.setViewportView(items);
        add(scrollPane);
    }
}
