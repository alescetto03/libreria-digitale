package GUI.Components;

import Controller.AppController;
import GUI.SaveItemInCollectionGUI;

import java.awt.image.BufferedImage;
import java.io.ObjectStreamException;
import java.util.Map;

public class PublicationPanel extends HorizontalScrollpanePanel {
    AppController appController;
    String publication_doi;
    public PublicationPanel(AppController appController, Map<String, Object> publication, String buttonText) {
        super(publication.get("title").toString(), (BufferedImage) publication.get("Cover"), buttonText);
        this.appController = appController;
        this.publication_doi = publication.get("doi").toString();
    }


    @Override
    public boolean onSaveButtonPress() {
        appController.switchView(new SaveItemInCollectionGUI(appController, null, this.publication_doi, appController.getCurrentView()));
        return false;
    }

    @Override
    public void onViewButtonPress() {
    }
}
