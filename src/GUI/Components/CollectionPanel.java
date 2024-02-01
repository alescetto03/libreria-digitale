package GUI.Components;

import Controller.AppController;

import java.util.Map;

public class CollectionPanel extends HorizontalScrollpanePanel{
    AppController appController;
    Object collectionId;
    public CollectionPanel(AppController appController, Map<String, Object> collection, String buttonText) {
        super(collection.get("name").toString(), buttonText);
        this.appController = appController;
        this.collectionId = collection.get("id");

    }
    @Override
    public boolean onSaveButtonPress() {
        return appController.saveSearchedCollection((Integer)this.collectionId);
    }

    @Override
    public void onViewButtonPress() {
        appController.showCollectionItems(this.collectionId);
    }
}
