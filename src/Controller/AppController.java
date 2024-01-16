package Controller;

import DAO.*;
import GUI.*;
import Model.*;
import GUI.Components.*;
import PostgresImplementationDAO.CollectionDAO;
import PostgresImplementationDAO.*;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppController {
    JFrame currentWindow;
    UserDAOInterface userDAO = new UserDAO();
    NotificationDAOInterface notificationDAO = new NotificationDAO();
    BookDAOInterface bookDAO = new BookDAO();
    ScientificPublicationDAOInterface publicationDAO = new ScientificPublicationDAO();
    CollectionDAOInterface collectionDAO = new CollectionDAO();
    StoreDAOInterface storeDAO = new StoreDAO();

    /**
     * Utente correntemente autenticato all'applicativo
     */
    User loggedUser = null;

    /**
     * Lista di tutte le raccolte personali dell'utente
     */
    ArrayList<Collection> personalCollections = new ArrayList<>();

    /**
     * Lista di tutte le raccolte salvate dall'utente
     */
    ArrayList<Collection> savedCollections = new ArrayList<>();

    ArrayList<Notification> userNotification = new ArrayList<Notification>();

    ArrayList<Book> searchedBook = new ArrayList<Book>();
    ArrayList<ScientificPublication> searchedPublication = new ArrayList<ScientificPublication>();
    ArrayList<Collection> searchedCollection = new ArrayList<Collection>();
    ArrayList<Store> storeWithCompleteSeries = new ArrayList<Store>();

    public static void main(String[] args) { (new AppController()).showLogin(); }
    //public static void main(String[] args) { AppController appController = new AppController(); appController.showSearchResults("signore"); }

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

    public void logoutUser() {
        loggedUser = null;
        switchView(new LoginGUI(this));
    }

    public void showHomepage() {
        getUserPersonalCollections();
        getUserSavedCollections();
        getUserNotification();

        ArrayList<AbstractModel> abstractModelsCollections = new ArrayList<>(personalCollections); //Effettuo una conversione perché ArrayList<Collection> non è sottotipo di ArrayList<AbstractModel>
        ArrayList<AbstractModel> abstractModelsCollectionSaved = new ArrayList<>(savedCollections);
        ArrayList<AbstractModel> abstractModelsNotification = new ArrayList<>(userNotification);

        ArrayList<Map<String, Object>> renderedPersonalCollections = renderData(abstractModelsCollections);
        ArrayList<Map<String, Object>> renderedPersonalCollectionsSaved = renderData(abstractModelsCollectionSaved);
        ArrayList<Map<String, Object>> renderedUserNotification = renderData(abstractModelsNotification);

        switchView(new HomepageGUI(this, renderedPersonalCollections, renderedUserNotification, renderedPersonalCollectionsSaved));
    }

    public void getUserPersonalCollections() {
        ArrayList<CollectionResultInterface> results = this.collectionDAO.getUserPersonalCollections(loggedUser.getUsername());

        this.personalCollections.clear();
        for (CollectionResultInterface result: results) {
            Collection collection = new Collection(result.getId(), result.getName(), loggedUser.getUsername(), Collection.Visibility.valueOf(result.getVisibility().toUpperCase()));
            this.personalCollections.add(collection);
        }
    }

    public void getUserSavedCollections(){
        ArrayList<CollectionResultInterface> results = this.collectionDAO.getUserSavedCollections(loggedUser.getUsername());

        this.savedCollections.clear();
        for(CollectionResultInterface result : results){
            Collection collection = new Collection(result.getId(), result.getName(), result.getOwner(), Collection.Visibility.valueOf(result.getVisibility().toUpperCase()));
            this.savedCollections.add(collection);
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
    
    public void getUserNotification(){
        ArrayList<NotificationResultInterface> results = this.notificationDAO.getUserNotification(loggedUser.getUsername());
        for(NotificationResultInterface result : results){
            Notification notification = new Notification(result.getText(), (result.getDate_time()).toLocalDateTime());
            this.userNotification.add(notification);
        }
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

    public boolean removeSavedCollectionFromDatabase(int id){
        if(collectionDAO.deleteSavedCollectionById(id, loggedUser.getUsername())){
            for (Collection savedCollection: savedCollections) {
                if (savedCollection.getId() == id) {
                    savedCollections.remove(savedCollection);
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

    public void getBookByString(String searchItem){
        ArrayList<BookResultInterface> results = this.bookDAO.getResearchedBook(searchItem);
        searchedBook.clear();

        for(BookResultInterface result : results){
            Book book = new Book(result.getIsbn(), result.getTitle(), result.getPublisher(), Book.FruitionMode.valueOf(result.getFruition_mode().toUpperCase()), result.getPublication_year(), null, result.getDescription(), Book.BookType.valueOf(result.getBook_type().toUpperCase()), result.getGenre(), result.getTarget(), result.getTopic());
            this.searchedBook.add(book);
        }
    }

    public void getScientificPublicationByString(String searchItem){
        ArrayList<ScientificPublicationResultInterface> results = this.publicationDAO.getResearchedPublication(searchItem);
        searchedPublication.clear();

        for(ScientificPublicationResultInterface result : results){
            ScientificPublication publication = new ScientificPublication(result.getDoi(), result.getTitle(), ScientificPublication.FruitionMode.valueOf(result.getFruition_mode().toUpperCase()), result.getPublication_year(), null, result.getDescription(), result.getPublisher());
            this.searchedPublication.add(publication);
        }
    }

    public void getCollectionByString(String searchItem){
        ArrayList<CollectionResultInterface> results = this.collectionDAO.getReasearchedCollection(searchItem);
        searchedCollection.clear();

        for(CollectionResultInterface result : results){
            Collection collection = new Collection(result.getId(), result.getName(), result.getOwner(), Collection.Visibility.valueOf(result.getVisibility().toUpperCase()));
            this.searchedCollection.add(collection);
        }
    }

    public ArrayList<String> getSeriesByString(String searchItem){
        return this.bookDAO.getResearchedSeries(searchItem);
    }

    public void getStoreCompleteSeries(String searchItem){
        ArrayList<StoreResultInterface> results = this.storeDAO.storeCompleteSerie(searchItem);
        storeWithCompleteSeries.clear();

        for(StoreResultInterface result : results){
            Store store = new Store(result.getPartita_iva(), result.getName(), result.getAddress(), result.getUrl());
            this.storeWithCompleteSeries.add(store);
        }
    }

    public void showSearchResults(String searchText){
        getBookByString(searchText);
        getScientificPublicationByString(searchText);
        getCollectionByString(searchText);

        ArrayList<AbstractModel> abstractModelsBooks = new ArrayList<>(searchedBook);
        ArrayList<AbstractModel> abstractModelsPublications = new ArrayList<>(searchedPublication);
        ArrayList<AbstractModel> abstractModelsCollections = new ArrayList<>(searchedCollection);

        ArrayList<Map<String, Object>> renderedSearchedBooks = renderData(abstractModelsBooks);
        ArrayList<Map<String, Object>> renderedSearchedPublications = renderData(abstractModelsPublications);
        ArrayList<Map<String, Object>> renderedSearchedCollections = renderData(abstractModelsCollections);

        for(Map<String, Object> item : renderedSearchedBooks){
            System.out.println(item.get("title"));
        }
        for(Map<String, Object> item : renderedSearchedPublications){
            System.out.println(item.get("title"));
        }
        for(Map<String, Object> item : renderedSearchedCollections){
            System.out.println(item.get("name"));
        }
        Map<String, Map<String, Object>> storeBySeries = new HashMap<>();
        for(String item : getSeriesByString(searchText)){
            getStoreCompleteSeries(item);
            storeBySeries.put(item, storeWithCompleteSeries.get(0).getData());

            //System.out.println(item);
        }
        System.out.println("NEGOZI CON SERIE COMPLETE:" + storeBySeries);
        showView(new ResultPage(this, renderedSearchedBooks));

    }
}