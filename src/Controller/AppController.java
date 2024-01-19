package Controller;

import DAO.*;
import GUI.*;
import Model.*;
import GUI.Components.*;
import PostgresImplementationDAO.CollectionDAO;
import PostgresImplementationDAO.*;

import javax.swing.*;
import javax.swing.text.View;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppController {
    /**
     * Finestra corrente dell'applicativo
     */
    JFrame currentWindow;

    /**
     * View corrente in cui ci troviamo
     */
    AppView currentView;

    /**
     * Interfaccia utenteDAO per la comunicazione col DB
     */
    UserDAOInterface userDAO = new UserDAO();

    /**
     * Interfaccia notificaDAO per la comunicazione col DB
     */
    NotificationDAOInterface notificationDAO = new NotificationDAO();

    /**
     * Interfaccia libroDAO per la comunicazione col DB
     */
    BookDAOInterface bookDAO = new BookDAO();

    /**
     * Interfaccia articoliScientificiDAO per la comunicazione col DB
     */
    ScientificPublicationDAOInterface publicationDAO = new ScientificPublicationDAO();

    /**
     * Interfaccia raccolteDAO per la comunicazione col DB
     */
    CollectionDAOInterface collectionDAO = new CollectionDAO();

    /**
     * Interfaccia negozioDAO per la comunicazione col DB
     */
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

    /**
     * Lista di tutte le notifiche dell'utente loggato
     */
    ArrayList<Notification> userNotification = new ArrayList<Notification>();

    /**
     * Lista di tutti i libri che un utente ricerca nella HomepageGUI
     */
    ArrayList<Book> searchedBook = new ArrayList<Book>();

    /**
     * Lista di tutti gli articoliScientifici che un utente ricerca nella HomepageGUI
     */
    ArrayList<ScientificPublication> searchedPublication = new ArrayList<ScientificPublication>();

    /**
     * Lista di tutte le raccolte che un utente ricerca nella HomepageGUI
     */
    ArrayList<Collection> searchedCollection = new ArrayList<Collection>();

    /**
     * Lista di tutti i negozi con serie complete che un utente ricerca nella HomepageGUI
     */
    ArrayList<Store> storeWithCompleteSeries = new ArrayList<Store>();

    public static void main(String[] args) { (new AppController()).showLogin(); }
    //public static void main(String[] args) { AppController appController = new AppController(); appController.showSearchResults("signore"); }

    /**
     * Funzione che ci permette di reperire la finestra corrente in cui ci troviamo
     * anche se siamo fuori dal controller.
     */
    public JFrame getCurrentWindow(){
        return this.currentWindow;
    }

    /**
     * Funzione che ci permette di reperire la view corrente in cui ci troviamo
     * anche se siamo fuori dal controller.
     */
    public AppView getCurrentView(){
        return this.currentView;
    }

    /**
     * Funzione per avere l'utente registrato all'applicativo in quel momento
     */
    public String getLoggedUsername(){
        return this.loggedUser.getUsername();
    }

    /**
     * Funzione per la chiusura della view corrente
     */
    public void closeCurrentView() {
        currentWindow.dispose();
        currentView = null;
    }

    /**
     * Funzione che ci permette di mostrare la finestra corrente
     */
    public void showView(AppView view) {
        currentView = view;
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

    /**
     * Funzione per mostrare le schermata di LoginGUI nonche la prima pagina da mostrare
     */
    public void showLogin() { showView(new LoginGUI(this)); }

    /**
     * Funzione per mostrare la schermata di HomepageGUI
     */
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

    /**
     * Funzione per cambiare schermata e chiudere quella precedente, uno switch di finestra in pratica.
     */
    public void switchView(AppView destinationView) {
        closeCurrentView();
        showView(destinationView);
    }







    /**
     * Funzione per autenticare l'utente, controlla nel DB le credenziali cripatate
     * se l'utente sbaglia credenziali viene mostrato un messaggio di errore, altrimenti
     * viene rimandato alla HomepageGUI
     */
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

    /**
     * Funzione per permettere ad un utente di registrarsi
     */
    public boolean registerUser(String username, String email, String password, String name, String surname, java.util.Date birthdate) {
        java.sql.Date sqlDate = new java.sql.Date(birthdate.getTime());
        UserResultInterface userResult = this.userDAO.register(username, email, password.getBytes(StandardCharsets.UTF_8), name, surname, sqlDate);

        if (userResult != null){
            loggedUser = new User(userResult.getUsername(), userResult.getEmail(), userResult.getName(), userResult.getSurname(), userResult.getBirthdate(), userResult.isAdmin());
            return true;
        }
        return false;
    }

    /**
     * Funzione per effettuare il logout di un utente
     */
    public void logoutUser() {
        loggedUser = null;
        switchView(new LoginGUI(this));
    }

    /**
     * Funzione per prendere tutte le collezzioni personali di un utente e caricarle in un ArrayList
     */
    public boolean getUserPersonalCollections() {
        ArrayList<CollectionResultInterface> results = this.collectionDAO.getUserPersonalCollections(getLoggedUsername());
        this.personalCollections.clear();
        if(!results.isEmpty()) {
            for (CollectionResultInterface result : results) {
                Collection collection = new Collection(result.getId(), result.getName(), getLoggedUsername(), Collection.Visibility.valueOf(result.getVisibility().toUpperCase()));
                this.personalCollections.add(collection);
            }
            return true;
        }
        return false;
    }

    /**
     * Funzione per prendere tutte le collezzioni personali di un utente sottoforma di ArrayList in modo tale
     * da passarle alle GUI
     */
    public ArrayList<Map<String, Object>> getUserPersonalCollectionsList(){
        ArrayList<AbstractModel> abstractModelsCollections = new ArrayList<>(this.personalCollections);
        return renderData(abstractModelsCollections);
    }

    /**
     * Funzione per prendere tutte le collezzioni salvate da un utente e inserirle in un ArrayList
     */
    public boolean getUserSavedCollections(){
        ArrayList<CollectionResultInterface> results = this.collectionDAO.getUserSavedCollections(getLoggedUsername());
        this.savedCollections.clear();

        if (!results.isEmpty()) {
            for (CollectionResultInterface result : results) {
                Collection collection = new Collection(result.getId(), result.getName(), result.getOwner(), Collection.Visibility.valueOf(result.getVisibility().toUpperCase()));
                this.savedCollections.add(collection);
            }
            return true;
        }
        return false;
    }

    /**
     * Funzione che prende tutte le notifiche di un utente loggato e le inserisce in un ArrayList
     */
    public void getUserNotification(){
        ArrayList<NotificationResultInterface> results = this.notificationDAO.getUserNotification(getLoggedUsername());
        userNotification.clear();

        if(!results.isEmpty()) {
            for (NotificationResultInterface result : results) {
                Notification notification = new Notification(result.getText(), (result.getDate_time()).toLocalDateTime());
                this.userNotification.add(notification);
            }
        }
    }

    /**
     * Funzione che prende tutti i libri ricercati tramite il titolo
     * poi li inserisce in un ArrayList
     */
    public void getBookByString(String searchItem){
        ArrayList<BookResultInterface> results = this.bookDAO.getResearchedBook(searchItem);
        searchedBook.clear();

        for(BookResultInterface result : results){
            Book book = new Book(result.getIsbn(), result.getTitle(), result.getPublisher(), Book.FruitionMode.valueOf(result.getFruition_mode().toUpperCase()), result.getPublication_year(), null, result.getDescription(), Book.BookType.valueOf(result.getBook_type().toUpperCase()), result.getGenre(), result.getTarget(), result.getTopic());
            this.searchedBook.add(book);
        }
    }

    /**
     * Funzione che prende tutti gli articoliScientifici ricercati tramite il nome
     * poi li inserisce in un ArrayList
     */
    public void getScientificPublicationByString(String searchItem){
        ArrayList<ScientificPublicationResultInterface> results = this.publicationDAO.getResearchedPublication(searchItem);
        searchedPublication.clear();

        for(ScientificPublicationResultInterface result : results){
            ScientificPublication publication = new ScientificPublication(result.getDoi(), result.getTitle(), ScientificPublication.FruitionMode.valueOf(result.getFruition_mode().toUpperCase()), result.getPublication_year(), null, result.getDescription(), result.getPublisher());
            this.searchedPublication.add(publication);
        }
    }

    /**
     * Funzione che prende tutte le raccolte ricercate tramite il nome
     * poi li inserisce in un ArrayList
     */
    public void getCollectionByString(String searchItem){
        ArrayList<CollectionResultInterface> results = this.collectionDAO.getReasearchedCollection(searchItem, getLoggedUsername());
        searchedCollection.clear();

        for(CollectionResultInterface result : results){
            Collection collection = new Collection(result.getId(), result.getName(), result.getOwner(), Collection.Visibility.valueOf(result.getVisibility().toUpperCase()));
            this.searchedCollection.add(collection);
        }
    }

    /**
     * Funzione che prende tutte le serie dato il nome e restituisce
     * un ArrayList con i nomi delle serie risultanti
     */
    public ArrayList<String> getSeriesByString(String searchItem){
        return this.bookDAO.getResearchedSeries(searchItem);
    }

    /**
     * Funzione che prende tutti i negozi che possiedono una serie completa
     * li inserisce poi in un ArrayList
     */
    public void getStoreCompleteSeries(String searchItem){
        ArrayList<StoreResultInterface> results = this.storeDAO.storeCompleteSerie(searchItem);
        storeWithCompleteSeries.clear();

        for(StoreResultInterface result : results){
            Store store = new Store(result.getPartita_iva(), result.getName(), result.getAddress(), result.getUrl());
            this.storeWithCompleteSeries.add(store);
        }
    }

    /**
     * Funzione che restituisce un ArrayList di libri e che prende dal DB
     * tutti i libri da una raccolta dato il suo ID
     */
    public ArrayList<Book> getBookListFromCollection(int collection_id){
        ArrayList<BookResultInterface> resultsBook = this.bookDAO.getBooksFromCollection(collection_id);
        ArrayList<Book> outputBook = new ArrayList<Book>();

        for(BookResultInterface result : resultsBook){
            //SIA QUI CHE SOPRA BISGONA AGGIUSTARE GESTENDO INSERIMENTO DI IMMAGINI
            Book book = new Book(result.getIsbn(), result.getTitle(), result.getPublisher(), Book.FruitionMode.valueOf(result.getFruition_mode().toUpperCase()), result.getPublication_year(), null, result.getDescription(),  Book.BookType.valueOf(result.getBook_type().toUpperCase()), result.getGenre(), result.getTarget(), result.getTopic());
            outputBook.add(book);
        }
        return outputBook;
    }

    /**
     * Funzione che restituisce un ArrayList di artioliScientifici e che prende dal DB
     * tutti gli articoli da una raccolta dato il suo ID
     */
    public ArrayList<ScientificPublication> getPublicationListFromCollection(int collection_id){
        ArrayList<ScientificPublicationResultInterface> resultsPublication = this.publicationDAO.getPublicationsFromCollection(collection_id);
        ArrayList<ScientificPublication> outputPublication = new ArrayList<ScientificPublication>();

        for(ScientificPublicationResultInterface result : resultsPublication){
            //SIA QUI CHE SOPRA BISGONA AGGIUSTARE GESTENDO INSERIMENTO DI IMMAGINI
            ScientificPublication publication = new ScientificPublication(result.getDoi(), result.getTitle(), ScientificPublication.FruitionMode.valueOf(result.getFruition_mode().toUpperCase()), result.getPublication_year(), null, result.getDescription(), result.getPublisher());
            outputPublication.add(publication);
        }
        return outputPublication;
    }

    /**
     * Funzione che restituisce un ArrayList di raccolte e che prende dal DB
     * tutti i dati di una raccolta dato il suo ID
     */
    public ArrayList<Collection> getAllFromCollectionById(int collection_id){
        CollectionResultInterface resultCollection = this.collectionDAO.getAllByCollectionId(collection_id);
        ArrayList<Collection> outputCollection = new ArrayList<Collection>();

        Collection collection = new Collection(resultCollection.getId(), resultCollection.getName(), resultCollection.getOwner(), Collection.Visibility.valueOf(resultCollection.getVisibility().toUpperCase()));
        outputCollection.add(collection);

        return outputCollection;
    }

    /**
     * Funzione che rimuove dal DB una raccolta dato il suo ID
     */
    public boolean removeCollectionFromDatabase(Object id) {
        int collection_id = Integer.parseInt((String)id);
        if (collectionDAO.deleteCollectionById(collection_id)) {
            for (Collection personalCollection: personalCollections) {
                if (personalCollection.getId() == collection_id) {
                    personalCollections.remove(personalCollection);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Funzione che rimuove dal DB le collezzioni salvate dell'utente loggato dato il suo ID
     */
    public boolean removeSavedCollectionFromDatabase(Object id){
        int collection_id = Integer.parseInt((String)id);
        if(collectionDAO.deleteSavedCollectionById(collection_id, getLoggedUsername())){
            for (Collection savedCollection: savedCollections) {
                if (savedCollection.getId() == collection_id) {
                    savedCollections.remove(savedCollection);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Funzione che rimuove dal DB un libro, dato il suo isbn,
     * da una raccolta, dato il suo ID
     */
    public boolean removeBookFromCollection(Object book_isbn, int collection_id){
        String isbn = (String) book_isbn;
        if (this.collectionDAO.deleteBookFromCollection(collection_id, isbn))
            return getUserSavedCollections();
        return false;
    }

    /**
     * Funzione che rimuove dal DB un articoloScientifico, dato il suo doi,
     * da una raccolta, dato il suo ID
     */
    public boolean removePublicationFromCollection(Object publication_doi, int collection_id){
        String doi = (String) publication_doi;
        //getUserSavedCollections();
        return this.collectionDAO.deletePublicationFromCollection(collection_id, doi);
    }

    /**
     * Funzione che salva nel DB le collezzioni personali create dall'utente.
     */
    public int savePersonalCollectionIntoDatabase(ArrayList<String> data) {
        if (data.get(1).isEmpty() || data.get(2).isEmpty()) {
            return 0;
        }
        String name = data.get(1);
        Collection.Visibility visibility = Collection.Visibility.valueOf(data.get(2).toUpperCase());
        if (!data.get(0).isEmpty()) {
            int id = Integer.parseInt(data.getFirst());
            CollectionResult collectionResultUpdated = collectionDAO.updateCollectionById(id, name, visibility, getLoggedUsername());
            if (collectionResultUpdated.getId() != 0) {
                for (Collection personalCollection : personalCollections) {
                    if (personalCollection.getId() == id) {
                        personalCollection.setName(name);
                        personalCollection.setVisibility(visibility);
                        break;
                    }
                }
                return collectionResultUpdated.getId();
            }
            return 0;
        }
        CollectionResult collectionResultInserted = collectionDAO.insertCollection(name, visibility, getLoggedUsername());
        return collectionResultInserted.getId();
    }

    /**
     * Funzione che salva nel DB una raccolta ricercata all'interno delle
     * raccolte preferite/salvate dell'utente.
     */
    public boolean saveSearchedCollection(int collection_id){
        for (Collection collection : this.savedCollections){
            if(collection.getId() == collection_id)
                return false;
        }

        if(this.collectionDAO.saveCollectionById(collection_id, getLoggedUsername())){
            getUserSavedCollections();
            return true;
        }
        return false;
    }

    /**
     * Funzione che salva nel DB un libro, dato il suo isbn,
     * all'interno di una raccolta, dato il suo ID
     */
    public boolean saveBookInCollection(int collection_id, String book_isbn){
        if(!this.collectionDAO.isBookInCollection(collection_id, book_isbn)){
            return this.collectionDAO.insertBookInCollection(collection_id, book_isbn);
        }
        return false;
    }

    /**
     * Funzione che salva nel DB un articoloScientifico, dato il suo doi,
     * all'interno di una raccolta, dato il suo ID
     */
    public boolean savePublicationInCollection(int collection_id, String publication_doi){
        if(!this.collectionDAO.isPublicationInCollection(collection_id, publication_doi)){
            return this.collectionDAO.insertPublicationInCollection(collection_id, publication_doi);
        }
        return false;
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





    /**
     * Funzione che mostra una schermata con tutti i libri contenuti in una raccolta
     */
    public void showCollectionItems(Object id){
        int collection_id = Integer.parseInt((String)id);

        ArrayList<Collection> collection_data = getAllFromCollectionById(collection_id);
        ArrayList<Book> booksInCollection = getBookListFromCollection(collection_id);
        ArrayList<ScientificPublication> publicationsInCollection = getPublicationListFromCollection(collection_id);

        ArrayList<AbstractModel> abstractModelsBook = new ArrayList<>(booksInCollection);
        ArrayList<AbstractModel> abstractModelsPublication = new ArrayList<>(publicationsInCollection);
        ArrayList<AbstractModel> abstractModelsCollection = new ArrayList<>(collection_data);

        ArrayList<Map<String, Object>> renderedBook = renderData(abstractModelsBook);
        ArrayList<Map<String, Object>> renderedPublication = renderData(abstractModelsPublication);
        ArrayList<Map<String, Object>> renderedCollection = renderData(abstractModelsCollection);

        switchView(new CollectionsGUI(this, renderedCollection.getFirst(), renderedBook, renderedPublication, this.currentView));
    }

    /**
     * Funzione che mostra una schermata con tutti i risultati di ricerca.
     * L'utente cerca in base ad un testo le possibili raccolte, libri, articoli e poi
     * le serie con relativi negozi che posseggono quella serie in vendita completa.
     */
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
        Map<String, String> storeBySeries = new HashMap<>();
        for(String item : getSeriesByString(searchText)){
            getStoreCompleteSeries(item);
            for(Store store : storeWithCompleteSeries)
                storeBySeries.put(item, store.getName());
        }
        //System.out.println("NEGOZI CON SERIE COMPLETE:" + storeBySeries);
        switchView(new SearchResultsGUI(this, renderedSearchedBooks, renderedSearchedPublications, renderedSearchedCollections, storeBySeries, this.currentView));

    }
}