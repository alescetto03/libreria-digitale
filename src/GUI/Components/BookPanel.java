package GUI.Components;

import Controller.AppController;
import GUI.AppView;
import GUI.CollectionsGUI;
import GUI.SaveItemInCollectionGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class BookPanel extends HorizontalScrollpanePanel {
    AppController appController;
    String book_isbn;
    public BookPanel(AppController appController, Map<String, Object> book, String buttonText) {
        super(book.get("title").toString(), (BufferedImage) book.get("cover"), buttonText);
        this.appController = appController;
        this.book_isbn = book.get("isbn").toString();
    }

    @Override
    public boolean onSaveButtonPress() {
        appController.switchView(new SaveItemInCollectionGUI(appController, this.book_isbn, null, appController.getCurrentView()));
        return true;
    }

    @Override
    public void onViewButtonPress() {
    }
}
