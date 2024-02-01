package GUI.Components;

import GUI.AppView;
import GUI.ModelManipulationFormGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public abstract class CrudTable extends JPanel {
    protected String[] columns;
    protected ArrayList<Map<String, Object>> data;
    protected JTable items = new JTable();
    protected AppView parentView;
    protected ActionButton createButton;
    protected ModelManipulationFormGUI createView;
    protected ModelManipulationFormGUI updateView;
    protected String updateViewTitle;
    protected JPanel topBar = new JPanel();
    protected ActionListener createButtonListener = (ActionEvent e) -> {
        parentView.getAppController().switchView(createView);
    };
    protected abstract DefaultTableModel getModel();
    public abstract boolean onRemoveButton(Object id);
    protected abstract void onUpdateButton(Object id, ArrayList<String> data);
    protected abstract void onViewButton(Object id);
    protected abstract Map<String, JComponent> getFormSchema();
    protected abstract Map<String, JComponent> getFormSchema(ArrayList<String> data);
    public CrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data, boolean displayViewButton, boolean displayEditButton, boolean displayCreateButton, boolean displayDeleteButton) {
        this.columns = columns;
        this.data = data;
        this.parentView = parentView;

        items.setDefaultEditor(Object.class, null);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setLayout(new BorderLayout());
        topBar.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        topBar.add(titleLabel, BorderLayout.LINE_START);

        if (displayCreateButton) {
            createButton = new ActionButton("/GUI/images/create.png", 18, 18, Image.SCALE_SMOOTH);
            topBar.add(createButton, BorderLayout.LINE_END);
        }
        add(topBar, BorderLayout.NORTH);
        add(items, BorderLayout.CENTER);
        DefaultTableModel model = getModel();
        items.setModel(model);
        items.getTableHeader().setReorderingAllowed(false);
        items.setRowHeight(40);
        items.setRowSelectionAllowed(false);
        items.setSelectionBackground(items.getBackground());
        model.addColumn("azioni", new Object[model.getRowCount()]);
        items.getColumn("azioni").setCellRenderer(new TableActionsPanelRenderer(this,displayViewButton, displayEditButton, displayDeleteButton));
        items.getColumn("azioni").setCellEditor(new TableActionsPanelEditor(this, displayViewButton, displayEditButton, displayDeleteButton));
        items.getColumn("azioni").setMinWidth(80);
        JScrollPane scrollPane = new JScrollPane(items, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), 185));
        add(scrollPane);
    }

    public CrudTable(AppView parentView, String title, String[] columns, ArrayList<Map<String, Object>> data, boolean displayViewButton, boolean displayEditButton, boolean displayCreateButton, boolean displayDeleteButton, String createViewTitle, String updateViewTitle) {
        this(parentView, title, columns, data, displayViewButton, displayEditButton, displayCreateButton, displayDeleteButton);
        this.createView = new ModelManipulationFormGUI(parentView.getAppController(), parentView, getFormSchema(), createViewTitle);
        this.updateViewTitle = updateViewTitle;
        createButton.addActionListener(createButtonListener);
    }

    public void setData(ArrayList<Map<String, Object>> data) {
        this.data = data;
    }

    public AppView getParentView() {
        return parentView;
    }
}
