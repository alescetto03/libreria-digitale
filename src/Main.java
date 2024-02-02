import Controller.AppController;
import GUI.SaveItemInCollectionGUI;

public class Main {
    public static void main(String[] args) { (new AppController()).showLogin(); }

    /**
    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.switchView(new SaveItemInCollectionGUI(appController, "9780747545445", null, null));
    }**/
}
