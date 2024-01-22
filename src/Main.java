import Controller.AppController;
import GUI.AdminPageGUI;
import GUI.HomepageGUI;
import GUI.ModelManipulationFormGUI;

public class Main {
    //public static void main(String[] args) { (new AppController()).showLogin(); }

    /**
     public static void main(String[] args) {
        AppController appController = new AppController();
        appController.showView(new AdminPageGUI(appController, appController.getRenderedBooks()));
    }
    **/

    /**public static void main(String[] args) {
        AppController appController = new AppController();
        appController.authenticateUser("john_doe", "P@ssw0rd");
        appController.showHomepage();
    }**/

    /**public static void main(String[] args) {
        AppController appController = new AppController();
    }**/

    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.showView(new AdminPageGUI(appController, appController.getRenderedBooks()));
    }
}
