package GUI;

import Controller.AppController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

/**
 * View che permette all'utente di gestire le vendite di libri di un negozio
 */
public class BookSalesGUI extends AppView {
    JPanel contentPane = new JPanel();
    JPanel titlePanel = new JPanel();
    JLabel storeTitle = new JLabel();
    public BookSalesGUI(AppController appController, Map<String, Object> store, ArrayList<Map<String, Object>> bookSales, ArrayList<Map<String, Object>> books, AppView parentView) {
        super(appController);
        JButton goBackButton = new JButton("Torna indietro");
        goBackButton.addActionListener((ActionEvent e) -> {
            appController.switchView(new AdminPageGUI(appController, new StoresCrudTable(parentView, "Negozi:", new String[]{"partita iva", "nome", "indirizzo", "url"}, appController.getRenderedStores())));
        });

        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        storeTitle.setText(store.get("partita_iva") + " - " + store.get("name"));
        storeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(goBackButton);
        titlePanel.add(storeTitle);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(800, 500));

        JPanel itemsWrapper = new JPanel();
        itemsWrapper.setLayout(new BoxLayout(itemsWrapper, BoxLayout.Y_AXIS));
        itemsWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        ArrayList<JPanel> items = new ArrayList<>();
        for (Map<String, Object> book: books) {
            JPanel item = new JPanel();
            JLabel bookLabel = new JLabel(book.get("isbn") + " | " + book.get("title") + " | " + book.get("fruition_mode"));

            JLabel priceLabel = new JLabel("Prezzo:");
            JCheckBox checkBox = new JCheckBox();
            JSpinner priceField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE,0.01));
            priceField.setPreferredSize(new Dimension(100, 20));
            priceField.setValue(0);
            priceLabel.setVisible(false);
            priceField.setVisible(false);

            JLabel quantityLabel = new JLabel("Quantità:");
            JSpinner quantityField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE,1));
            quantityField.setPreferredSize(new Dimension(100, 20));
            quantityField.setValue(0);
            quantityLabel.setVisible(false);
            quantityField.setVisible(false);

            ActionButton saveButton = new ActionButton("/GUI/images/save.png", 18, 18, Image.SCALE_SMOOTH);
            for (Map<String, Object> bookSale: bookSales) {
                if (bookSale.get("book").equals(book)) {
                    checkBox.setSelected(true);
                    priceLabel.setVisible(true);
                    priceField.setVisible(true);
                    quantityLabel.setVisible(true);
                    quantityField.setVisible(true);
                    priceField.setValue(bookSale.get("price"));
                    quantityField.setValue(bookSale.get("quantity"));
                    break;
                }
            }

            item.setLayout(new FlowLayout(FlowLayout.LEFT));
            item.add(bookLabel);
            item.add(checkBox);
            item.add(priceLabel);
            item.add(priceField);
            item.add(quantityLabel);
            item.add(quantityField);
            item.add(saveButton);
            items.add(item);
            itemsWrapper.add(item);

            checkBox.addActionListener((ActionEvent e) -> {
                priceLabel.setVisible(checkBox.isSelected());
                priceField.setVisible(checkBox.isSelected());
                quantityLabel.setVisible(checkBox.isSelected());
                quantityField.setVisible(checkBox.isSelected());
            });
            saveButton.addActionListener((ActionEvent e) -> {
                try {
                    double price = Double.parseDouble(String.valueOf(priceField.getValue()));
                    int quantity = Integer.parseInt(String.valueOf(quantityField.getValue()));
                    appController.updateBookSale((String) book.get("isbn"), (String) store.get("partita_iva"), price, quantity, ((JCheckBox) saveButton.getParent().getComponent(1)).isSelected());
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(getContentPane(), exception.getMessage(), "!!!Errore!!!", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        contentPane.add(titlePanel, BorderLayout.PAGE_START);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(itemsWrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
