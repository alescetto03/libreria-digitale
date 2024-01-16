package Controller;

import DAO.CollectionDAOInterface;
import DAO.CollectionResultInterface;
import DAO.UserDAOInterface;
import DAO.UserResultInterface;
import GUI.AppView;
import GUI.Components.CrudTable;
import GUI.HomepageGUI;
import GUI.LoginGUI;
import Model.AbstractModel;
import Model.Collection;
import Model.User;
import PostgresImplementationDAO.CollectionDAO;
import PostgresImplementationDAO.UserDAO;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class AppController {
    JFrame currentWindow;
    UserDAOInterface userDAO = new UserDAO();
    CollectionDAOInterface collectionDAO = new CollectionDAO();

    /**
     * Utente correntemente autenticato all'applicativo
     */
    //User loggedUser = null;
    User loggedUser = new User("john_doe", "ciccio", "ciccio", "pippo", LocalDate.of(2000, 10, 3), false);

    /**
     * Lista di tutte le raccolte personali dell'utente
     */
    ArrayList<Collection> personalCollections = new ArrayList<>();

    /**
     * Lista di tutte le raccolte salvate dall'utente
     */
    ArrayList<Collection> savedCollections = new ArrayList<>();

    //public static void main(String[] args) { (new AppController()).showLogin(); }
    public static void main(String[] args) { AppController appController = new AppController(); appController.showHomepage(); }

    public void showView(AppView view) {
        currentWindow = new JFrame(view.getTitle());
        currentWindow.add(view.getContentPane());

        if (view.getDimension() != null) {
            currentWindow.setSize(view.getDimension());
        } else {
            currentWindow.pack();
        }
        currentWindow.setLocationRelativeTo(null);
        currentWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentWindow.setResizable(false);
        currentWindow.setVisible(true);
    }

    public void closeCurrentView() { currentWindow.dispose(); }

    public void switchView(AppView destinationView) {
        closeCurrentView();
        showView(destinationView);
    }

    public void showLogin() { showView(new LoginGUI(this)); }

    public boolean authenticateUser(String username, String password) {
        MessageDigest digest;
        try{
            digest = MessageDigest.getInstance("SHA-512");
        }catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return false;
        }
        byte[] hashedPassword = digest.digest(password.getBytes());

        UserResultInterface userResult = this.userDAO.login(username, hashedPassword);
        if (userResult != null){
            loggedUser = new User(userResult.getUsername(), userResult.getEmail(), userResult.getName(), userResult.getSurname(), userResult.getBirthdate(), userResult.isAdmin());
            return true;
        }
        return false;
    }

    public boolean registerUser(String username, String email, String password, String name, String surname, java.util.Date birthdate) {
        // Converti l'oggetto java.util.Date in java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(birthdate.getTime());
        UserResultInterface userResult = this.userDAO.register(username, email, password.getBytes(StandardCharsets.UTF_8), name, surname, sqlDate);
        if (userResult != null){
            loggedUser = new User(userResult.getUsername(), userResult.getEmail(), userResult.getName(), userResult.getSurname(), userResult.getBirthdate(), userResult.isAdmin());
            return true;
        }
        return false;
    }

    public void showHomepage() {
        getUserPersonalCollections();
        ArrayList<AbstractModel> abstractModels = new ArrayList<>(personalCollections); //Effettuo una conversione perché ArrayList<Collection> non è sottotipo di ArrayList<AbstractModel>
        ArrayList<Map<String, Object>> renderedPersonalCollections = renderData(abstractModels);
        showView(new HomepageGUI(this, renderedPersonalCollections));
    }

    public void getUserPersonalCollections() {
        ArrayList<CollectionResultInterface> results = this.collectionDAO.getUserPersonalCollections(loggedUser.getUsername());

        this.personalCollections.clear();
        for (CollectionResultInterface result: results) {
            Collection collection = new Collection(result.getId(), result.getName(), loggedUser.getUsername(), Collection.Visibility.valueOf(result.getVisibility().toUpperCase()));
            this.personalCollections.add(collection);
        }
    }

    /**
     * Funzione che renderizza i dati per renderli visualizzabili in una view
     * @see CrudTable
     */
    public ArrayList<Map<String, Object>> renderData(ArrayList<AbstractModel> objects) {
        ArrayList<Map<String, Object>> renderedData = new ArrayList<>();
        objects.forEach(object -> {
            renderedData.add(object.getData());
        });
        return renderedData;
    }

    public boolean removeCollectionFromDatabase(int id) {
        if (collectionDAO.deleteCollectionById(id)) {
            for (Collection personalCollection: personalCollections) {
                if (personalCollection.getId() == id) {
                    personalCollections.remove(personalCollection);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean savePersonalCollectionIntoDatabase(ArrayList<String> data) {
        if (data.get(1).equals("") || data.get(2).equals("")) {
            return false;
        }
        String name = data.get(1);
        Collection.Visibility visibility = Collection.Visibility.valueOf(data.get(2).toUpperCase());
        if (!data.get(0).equals("")) {
            int id = Integer.parseInt(data.get(0));
            if (collectionDAO.updateCollectionById(id, name, visibility, loggedUser.getUsername())) {
                for (Collection personalCollection: personalCollections) {
                    if (personalCollection.getId() == id) {
                        personalCollection.setName(name);
                        personalCollection.setVisibility(visibility);
                        break;
                    }
                }
                return true;
            }
            return false;
        }
        return collectionDAO.insertCollection(name, visibility, loggedUser.getUsername());
    }
}