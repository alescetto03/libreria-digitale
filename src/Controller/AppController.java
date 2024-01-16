package Controller;

import DAO.*;
import GUI.*;
import Model.*;
import PostgresImplementationDAO.CollectionDAO;
import PostgresImplementationDAO.*;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
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
    ArrayList<Store> storeCompleteSeries = new ArrayList<Store>();

    public static void main(String[] args) { (new AppController()).showLogin(); }
    //public static void main(String[] args) { AppController appController = new AppController(); appController.showHomepage(); }

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
            Book book = new Book(result.getIsbn(), result.getTitle(), result.getPublisher(), Book.FruitionMode.valueOf(result.getFruition_mode()), result.getPublication_year(), null, result.getDescription(), Book.BookType.valueOf(result.getBook_type()), result.getGenre(), result.getTarget(), result.getTopic());
            this.searchedBook.add(book);
        }
    }

    public void getScientificPublicationByString(String searchItem){
        ArrayList<ScientificPublicationResultInterface> results = this.publicationDAO.getResearchedPublication(searchItem);
        searchedPublication.clear();

        for(ScientificPublicationResultInterface result : results){
            ScientificPublication publication = new ScientificPublication(result.getDoi(), result.getTitle(), ScientificPublication.FruitionMode.valueOf(result.getFruition_mode()), result.getPublication_year(), null, result.getDescription(), result.getPublisher());
            this.searchedPublication.add(publication);
        }
    }

    public void getCollectionByString(String searchItem){
        ArrayList<CollectionResultInterface> results = this.collectionDAO.getReasearchedCollection(searchItem);
        searchedCollection.clear();

        for(CollectionResultInterface result : results){
            Collection collection = new Collection(result.getId(), result.getName(), result.getOwner(), Collection.Visibility.valueOf(result.getVisibility()));
            this.searchedCollection.add(collection);
        }
    }

    public ArrayList<String> getSeriesByString(String searchItem){
        return this.bookDAO.getResearchedSeries(searchItem);
    }

    public void getStoreCompleteSeries(String searchItem){
        ArrayList<StoreResultInterface> results = this.storeDAO.storeCompleteSerie(searchItem);
        storeCompleteSeries.clear();

        for(StoreResultInterface result : results){
            Store store = new Store(result.getPartita_iva(), result.getName(), result.getAddress(), result.getUrl());
            this.storeCompleteSeries.add(store);
        }
    }

    public void showSearchResults(String searchText){
        getBookByString(searchText);
        getScientificPublicationByString(searchText);
        getCollectionByString(searchText);

        ArrayList<AbstractModel> abstractModelsBook = new ArrayList<>(searchedBook);
        ArrayList<AbstractModel> abstractModelsPublication = new ArrayList<>(searchedPublication);
        ArrayList<AbstractModel> abstractModelsCollection = new ArrayList<>(searchedCollection);

        ArrayList<Map<String, Object>> renderedSearchedBook = renderData(abstractModelsBook);
        ArrayList<Map<String, Object>> renderedSearchedPublication = renderData(abstractModelsPublication);
        ArrayList<Map<String, Object>> renderedSearchedCollection = renderData(abstractModelsCollection);

        for(Map<String, Object> item : renderedSearchedBook){
            System.out.println(item.get("title"));
        }
        for(Map<String, Object> item : renderedSearchedPublication){
            System.out.println(item.get("title"));
        }
        for(Map<String, Object> item : renderedSearchedCollection){
            System.out.println(item.get("name"));
        }
        Map<String, Map<String, Object>> storeBySeries = new HashMap<>();
        for(String item : getSeriesByString(searchText)){
            getStoreCompleteSeries(item);
            storeBySeries.put(item, storeCompleteSeries.getFirst().getData());

            //System.out.println(item);
        }
        System.out.println("NEGOZI CON SERIE COMPLETE:" + storeBySeries);
        switchView(new ResultPage(this, renderedSearchedBook));

    }
}