package GUI.Components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class CrudTable extends JPanel implements TableModelListener {
    protected abstract DefaultTableModel getModel();
    public abstract boolean addRow(int position);
    public abstract boolean removeRow(int position);
    protected abstract void renamed(int position);
    public CrudTable(String title) {
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setLayout(new BorderLayout());
        JPanel topBar = new JPanel();
        topBar.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        ActionsPanel actionsPanel = new ActionsPanel(this, false, false, true, true);
        topBar.add(titleLabel, BorderLayout.LINE_START);
        topBar.add(actionsPanel, BorderLayout.LINE_END);
        add(topBar, BorderLayout.NORTH);

        createTable();
    }

    public void tableChanged(TableModelEvent e) {
        int index = e.getFirstRow();
        if (index != TableModelEvent.HEADER_ROW) {
            renamed(index);
        }
    }

    private void createTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), 185));
        JTable items = new JTable();
        add(items, BorderLayout.CENTER);
        DefaultTableModel model = getModel();
        items.setModel(model);
        model.addTableModelListener(this);
        items.getTableHeader().setReorderingAllowed(false);
        items.setRowHeight(40);
        items.setSelectionBackground(new java.awt.Color(56, 138, 112));
        items.getColumn("azioni").setCellRenderer(new TableActionsPanelRenderer(this,true, true, false, false));
        items.getColumn("azioni").setCellEditor(new TableActionsPanelEditor(this, true, true, false, false));
        scrollPane.setViewportView(items);
        add(scrollPane);
    }
}
