package Controller;

import DAO.*;
import GUI.*;
import Model.*;
import PostgresImplementationDAO.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
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
     * Interfaccia DAO per la comunicazione con la tabella utente del DB
     */
    UserDAOInterface userDAO = new UserDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella notifica del DB
     */
    NotificationDAOInterface notificationDAO = new NotificationDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella libro del DB
     */
    BookDAOInterface bookDAO = new BookDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella articoli scientifici del DB
     */
    ScientificPublicationDAOInterface publicationDAO = new ScientificPublicationDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella autore del DB
     */
    AuthorDAOInterface authorDAO = new AuthorDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella raccolte del DB
     */
    CollectionDAOInterface collectionDAO = new CollectionDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella negozio del DB
     */
    StoreDAOInterface storeDAO = new StoreDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella collana del DB
     */
    EditorialCollectionDAOInterface editorialCollectionDAO = new EditorialCollectionDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella serie del DB
     */
    SerieDAOInterface serieDAO = new SerieDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella rivista del DB
     */
    JournalDAOInterface journalDAO = new JournalDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella conferenza del DB
     */
    ConferenceDAOInterface conferenceDAO = new ConferenceDAO();

    /**
     * Interfaccia DAO per la comunicazione con la tabella sala del DB
     */
    PresentationHallDAOInterface presentationHallDAO = new PresentationHallDAO();

    /**
     * Utente correntemente autenticato all'applicativo
     */
    User loggedUser = null;

    /**
     * Lista di tutte le raccolte salvate dall'utente
     */
    ArrayList<Collection> savedCollections = new ArrayList<>();

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

    /**
     * Metodo che ci permette di reperire la finestra corrente in cui ci troviamo
     * anche se siamo fuori dal controller.
     */
    public JFrame getCurrentWindow(){
        return this.currentWindow;
    }

    /**
     * Metodo che ci permette di reperire la view corrente in cui ci troviamo
     * anche se siamo fuori dal controller.
     */
    public AppView getCurrentView(){
        return this.currentView;
    }

    /**
     * Metodo per avere l'utente registrato all'applicativo in quel momento
     */
    public String getLoggedUsername(){
        return this.loggedUser.getUsername();
    }

    /**
     * Metodo per la chiusura della view corrente
     */
    public void closeCurrentView() {
        currentWindow.dispose();
        currentView = null;
    }

    /**
     * Metodo che ci permette di istanziare una nuova view
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
     * Metodo per mostrare le schermata di LoginGUI
     */
    public void showLogin() { showView(new LoginGUI(this)); }

    /**
     * Metodo per mostrare la schermata di HomepageGUI
     */
    public void showHomepage() { switchView(new HomepageGUI(this)); }

    /**
     * Metodo per cambiare schermata e chiudere quella precedente, uno switch di finestra in pratica.
     */
    public void switchView(AppView destinationView) {
        closeCurrentView();
        showView(destinationView);
    }

    /**
     * Metodo che effettua l'autenticazione dell'utente, cerca un utente nel DB e controlla che le credenziali cripatate
     * inserite siano corrette, se l'autenticazione va a buon fine reindirizza l'utente alla HomepageGUI
     * @see HomepageGUI
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
     * Metodo che registra un utente all'applicativo
     */
    public boolean registerUser(String username, String email, String password, String name, String surname, java.util.Date birthdate) {
        Date sqlDate = new Date(birthdate.getTime());
        UserResultInterface userResult = this.userDAO.register(username, email, password.getBytes(StandardCharsets.UTF_8), name, surname, sqlDate);

        if (userResult != null){
            loggedUser = new User(userResult.getUsername(), userResult.getEmail(), userResult.getName(), userResult.getSurname(), userResult.getBirthdate(), userResult.isAdmin());
            return true;
        }
        return false;
    }

    /**
     * Metodo per effettuare il logout di un utente
     */
    public void logoutUser() {
        loggedUser = null;
        switchView(new LoginGUI(this));
    }


    /**
     * Metodo per prendere tutte le collezioni salvate da un utente e inserirle in un ArrayList
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
     * Metodo che prende tutti i libri ricercati tramite il titolo
     * poi li inserisce in un ArrayList
     */
    public void getBookByString(String searchItem){
        ArrayList<BookResultInterface> results = this.bookDAO.getResearchedBook(searchItem);
        searchedBook.clear();

        Book book = null;
        for(BookResultInterface result : results){
            if(result.getCover() != null) {
                try {
                    book = new Book(result.getIsbn(), result.getTitle(), result.getPublisher(), Book.FruitionMode.valueOf(result.getFruitionMode().toUpperCase()), result.getPublicationYear(), ImageIO.read(result.getCover()), result.getDescription(), Book.BookType.valueOf(result.getBookType().toUpperCase()), result.getGenre(), result.getTarget(), result.getTopic());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            else
                book = new Book(result.getIsbn(), result.getTitle(), result.getPublisher(), Book.FruitionMode.valueOf(result.getFruitionMode().toUpperCase()), result.getPublicationYear(), null, result.getDescription(), Book.BookType.valueOf(result.getBookType().toUpperCase()), result.getGenre(), result.getTarget(), result.getTopic());

            this.searchedBook.add(book);
        }
    }


    /**
     * Metodo che prende tutti gli articoliScientifici ricercati tramite il nome
     * poi li inserisce in un ArrayList
     */
    public void getScientificPublicationByString(String searchItem){
        ArrayList<ScientificPublicationResultInterface> results = this.publicationDAO.getResearchedPublication(searchItem);
        searchedPublication.clear();

        ScientificPublication publication = null;
        for(ScientificPublicationResultInterface result : results){
            if (result.getCover() != null){
                try{
                    publication = new ScientificPublication(result.getDoi(), result.getTitle(), ScientificPublication.FruitionMode.valueOf(result.getFruitionMode().toUpperCase()), result.getPublicationYear(), ImageIO.read(result.getCover()), result.getDescription(), result.getPublisher());
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
            else
                publication = new ScientificPublication(result.getDoi(), result.getTitle(), ScientificPublication.FruitionMode.valueOf(result.getFruitionMode().toUpperCase()), result.getPublicationYear(), null, result.getDescription(), result.getPublisher());
            this.searchedPublication.add(publication);
        }
    }

    /**
     * Metodo che prende tutte le raccolte ricercate tramite il nome
     * poi le inserisce in un ArrayList
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
     * Metodo che prende tutte le serie dato il nome e restituisce
     * un ArrayList con i nomi delle serie risultanti
     */
    public ArrayList<String> getSeriesByString(String searchItem){
        return this.serieDAO.getResearchedSeries(searchItem);
    }

    /**
     * Metodo che prende tutti i negozi che possiedono una serie completa
     * li inserisce poi in un ArrayList
     */
    public void getStoreCompleteSeries(String searchItem){
        ArrayList<StoreResultInterface> results = this.storeDAO.storeCompleteSerie(searchItem);
        storeWithCompleteSeries.clear();

        for(StoreResultInterface result : results){
            Store store = new Store(result.getPartitaIva(), result.getName(), result.getAddress(), result.getUrl());
            this.storeWithCompleteSeries.add(store);
        }
    }

    /**
     * Metodo che restituisce un ArrayList di libri e che prende dal DB
     * tutti i libri da una raccolta dato il suo ID
     */
    public ArrayList<Book> getBookListFromCollection(int collection_id){
        ArrayList<BookResultInterface> resultsBook = this.bookDAO.getBooksFromCollection(collection_id);
        ArrayList<Book> outputBook = new ArrayList<Book>();

        for(BookResultInterface result : resultsBook){
            Book book = new Book(result.getIsbn(), result.getTitle(), result.getPublisher(), Book.FruitionMode.valueOf(result.getFruitionMode().toUpperCase()), result.getPublicationYear(), null, result.getDescription(),  Book.BookType.valueOf(result.getBookType().toUpperCase()), result.getGenre(), result.getTarget(), result.getTopic());
            outputBook.add(book);
        }
        return outputBook;
    }

    /**
     * Metodo che restituisce un ArrayList di artioliScientifici e che prende dal DB
     * tutti gli articoli da una raccolta dato il suo ID
     */
    public ArrayList<ScientificPublication> getPublicationListFromCollection(int collection_id){
        ArrayList<ScientificPublicationResultInterface> resultsPublication = this.publicationDAO.getPublicationsFromCollection(collection_id);
        ArrayList<ScientificPublication> outputPublication = new ArrayList<ScientificPublication>();

        for(ScientificPublicationResultInterface result : resultsPublication){
            ScientificPublication publication = new ScientificPublication(result.getDoi(), result.getTitle(), ScientificPublication.FruitionMode.valueOf(result.getFruitionMode().toUpperCase()), result.getPublicationYear(), null, result.getDescription(), result.getPublisher());
            outputPublication.add(publication);
        }
        return outputPublication;
    }

    /**
     * Metodo che restituisce un ArrayList di raccolte e che prende dal DB
     * tutti i dati di una raccolta dato il suo ID
     */
    public ArrayList<Collection> getCollectionById(int collection_id){
        CollectionResultInterface resultCollection = this.collectionDAO.getCollectionById(collection_id);
        ArrayList<Collection> outputCollection = new ArrayList<Collection>();

        Collection collection = new Collection(resultCollection.getId(), resultCollection.getName(), resultCollection.getOwner(), Collection.Visibility.valueOf(resultCollection.getVisibility().toUpperCase()));
        outputCollection.add(collection);

        return outputCollection;
    }

    /**
     * Metodo che rimuove dal DB una raccolta dato il suo ID
     */
    public boolean removeCollectionFromDatabase(int id) {
        return collectionDAO.deleteCollectionById(id);
    }

    /**
     * Metodo che rimuove dal DB le collezzioni salvate dell'utente loggato dato il suo ID
     */
    public boolean removeSavedCollectionFromDatabase(int id){
        if(collectionDAO.deleteSavedCollectionById(id, getLoggedUsername())){
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

    /**
     * Metodo che rimuove un libro da una raccolta, dati ISBN e l'ID della raccolta
     */
    public boolean removeBookFromCollection(Object book_isbn, int collection_id){
        String isbn = (String) book_isbn;
        if (this.collectionDAO.deleteBookFromCollection(collection_id, isbn))
            return getUserSavedCollections();
        return false;
    }

    /**
     * Metodo che rimuove un articolo scientifico da una raccolta, dati il DOI e l'ID della raccolta
     */
    public boolean removePublicationFromCollection(Object publication_doi, int collection_id){
        String doi = (String) publication_doi;
        return this.collectionDAO.deletePublicationFromCollection(collection_id, doi);
    }

    /**
     * Metodo che inserisce una raccolta personale nel DB
     */
    public void insertPersonalCollectionIntoDatabase(String name, String visibility) {
        this.collectionDAO.insertCollection(name, Collection.Visibility.valueOf(visibility.toUpperCase()), loggedUser.getUsername());
    }

    /**
     * Metodo che inserisce un libro nel DB
     */
    public void insertBookIntoDatabase(String isbn, String title, String publisher, String fruition_mode, int publication_year, byte[] cover, String description, String genre, String book_type, String target, String topic) throws Exception{
            this.bookDAO.insertBookInDb(isbn, title, publisher, Book.FruitionMode.valueOf(fruition_mode.toUpperCase()), publication_year, cover, description, genre, target, topic, Book.BookType.valueOf(book_type.toUpperCase()));
    }

    /**
     * Metodo che inserisce un articolo scientifico nel DB
     */
    public void insertPublicationIntoDatabase(String doi, String title, String publisher, String fruition_mode, int publication_year, byte[] cover, String description) throws Exception{
            this.publicationDAO.insertPublicationInDb(doi, title, publisher, ScientificPublication.FruitionMode.valueOf(fruition_mode.toUpperCase()), publication_year, null, description);
    }

    /**
     * Metodo che inserisce un autore nel DB
     */
    public void insertAuthorIntoDatabase(String name, LocalDate birth_date, LocalDate death_date, String nationality, String bio) throws Exception{
        Date parsedBirthdate = birth_date != null ? Date.valueOf(birth_date) : null;
        Date parsedDeathDate = death_date != null ? Date.valueOf(death_date) : null;
        this.authorDAO.insertAuthorInDb(name, parsedBirthdate, parsedDeathDate, nationality, bio);
    }

    /**
     * Metodo che inserisce un negozio nel DB
     */
    public void insertStoreIntoDatabase(String partita_iva, String name, String address, String url) throws Exception{
            this.storeDAO.insertStoreInDb(partita_iva, name, address, url);
    }

    /**
     * Metodo che inserisce una collana nel DB
     */
    public void insertEditorialCollectionIntoDatabase(String issn, String name, String publisher) throws Exception{
            this.editorialCollectionDAO.insertEditorialCollectionInDb(issn, name, publisher);
    }

    /**
     * Metodo che inserisce una serie nel DB
     */
    public void insertSeriesIntoDatabase(String prequel, String sequel, String name) throws Exception{
            this.serieDAO.insertSeriesInDb(prequel, sequel, name);
    }

    /**
     * Metodo che inserisce una rivista nel DB
     */
    public void insertJournalsIntoDatabase(String issn, String name, String argument, int publication_year, String manager) throws Exception{
            this.journalDAO.insertJournalInDb(issn, name, argument, publication_year, manager);
    }

    /**
     * Metodo che inserisce una conferenza nel DB
     */
    public void insertConferenceIntoDatabase(String location, LocalDate start_date, LocalDate end_date, String organizer, String manager) throws Exception{
        Date parsedStartDate = start_date != null ? Date.valueOf(start_date) : null;
        Date parsedEndDate = end_date != null ? Date.valueOf(end_date) : null;
        this.conferenceDAO.insertConferenceInDb(location, parsedStartDate, parsedEndDate, organizer, manager);
    }

    /**
     * Metodo che inserisce una sala nel DB
     */
    public void insertPresentationHallIntoDatabase(String name, String address) throws Exception{
            this.presentationHallDAO.insertPresentationHallInDb(name, address);
    }

    /**
     * Metodo che aggiorna i dati di una raccolta personale nel DB
     */
    public void updatePersonalCollectionIntoDatabase(int id, String name, String visibility) {
        this.collectionDAO.updateCollectionById(id, name, Collection.Visibility.valueOf(visibility.toUpperCase()), loggedUser.getUsername());
    }

    /**
     * Metodo che permette all'utente loggato di salvare una raccolta
     */
    public boolean saveSearchedCollection(int collection_id) {
        for (Collection collection : this.savedCollections) {
            if (collection.getId() == collection_id)
                return false;
        }

        if (this.collectionDAO.saveCollectionById(collection_id, getLoggedUsername())) {
            getUserSavedCollections();
            return true;
        }
        return false;
    }

    /**
     * Metodo che salva un libro in una raccolta
     */
    public boolean saveBookInCollection(int collection_id, String book_isbn){
        if(!this.collectionDAO.isBookInCollection(collection_id, book_isbn)){
            return this.collectionDAO.insertBookInCollection(collection_id, book_isbn);
        }
        return false;
    }

    /**
     * Metodo che salva un articolo scientifico in una raccolta
     */
    public boolean savePublicationInCollection(int collection_id, String publication_doi){
        if(!this.collectionDAO.isPublicationInCollection(collection_id, publication_doi)){
            return this.collectionDAO.insertPublicationInCollection(collection_id, publication_doi);
        }
        return false;
    }

    /**
     * Metodo che renderizza i dati per renderli visualizzabili in una view
     */
    public ArrayList<Map<String, Object>> renderData(ArrayList<AbstractModel> objects) {
        ArrayList<Map<String, Object>> renderedData = new ArrayList<>();
        objects.forEach(object -> {
            renderedData.add(object.getData());
        });
        return renderedData;
    }

    /**
     * Metodo che mostra il contenuto di una raccolta
     */
    public void showCollectionItems(Object id){
        int collection_id = Integer.parseInt((String)id);

        ArrayList<Collection> collection_data = getCollectionById(collection_id);
        ArrayList<Book> booksInCollection = getBookListFromCollection(collection_id);
        ArrayList<ScientificPublication> publicationsInCollection = getPublicationListFromCollection(collection_id);

        ArrayList<AbstractModel> abstractModelsBook = new ArrayList<>(booksInCollection);
        ArrayList<AbstractModel> abstractModelsPublication = new ArrayList<>(publicationsInCollection);
        ArrayList<AbstractModel> abstractModelsCollection = new ArrayList<>(collection_data);

        ArrayList<Map<String, Object>> renderedBook = renderData(abstractModelsBook);
        ArrayList<Map<String, Object>> renderedPublication = renderData(abstractModelsPublication);
        ArrayList<Map<String, Object>> renderedCollection = renderData(abstractModelsCollection);

        switchView(new CollectionsGUI(this, renderedCollection.get(0), renderedBook, renderedPublication));
    }

    /**
     * Metodo che mostra una schermata con tutti i risultati di ricerca.
     * L'utente cerca in base ad un testo le possibili raccolte, libri, articoli scientifici e poi
     * le serie con relativi negozi che posseggono quella serie in vendita completa.
     */
    public void showSearchResults(String searchText) {
        getBookByString(searchText);
        getScientificPublicationByString(searchText);
        getCollectionByString(searchText);

        ArrayList<AbstractModel> abstractModelsBooks = new ArrayList<>(searchedBook);
        ArrayList<AbstractModel> abstractModelsPublications = new ArrayList<>(searchedPublication);
        ArrayList<AbstractModel> abstractModelsCollections = new ArrayList<>(searchedCollection);

        ArrayList<Map<String, Object>> renderedSearchedBooks = renderData(abstractModelsBooks);
        ArrayList<Map<String, Object>> renderedSearchedPublications = renderData(abstractModelsPublications);
        ArrayList<Map<String, Object>> renderedSearchedCollections = renderData(abstractModelsCollections);

        Map<String, String> storeBySeries = new HashMap<>();
        for(String item : getSeriesByString(searchText)){
            if(!item.isEmpty()) {
                getStoreCompleteSeries(item);
                if (!storeWithCompleteSeries.isEmpty()) {
                    for (Store store : storeWithCompleteSeries)
                        storeBySeries.put(item, store.getName());
                } else
                    storeBySeries.put(item, "Non ci sono negozi che vendono tutta la serie.");
            }
            else
                storeBySeries.put("Non ci sono serie con questo nome.", "Non ci sono negozi che vendono tutta la serie.");
        }
        switchView(new SearchResultsGUI(this, renderedSearchedBooks, renderedSearchedPublications, renderedSearchedCollections, storeBySeries, this.currentView));
    }

    /**
     * Metodo che restituisce una lista di tutte le notifiche dell'utente loggato memorizzate nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedNotifications() {
        ArrayList<NotificationResultInterface> notificationResults = this.notificationDAO.getUserNotification(getLoggedUsername());
        ArrayList<AbstractModel> notifications = new ArrayList<>();
        for (NotificationResultInterface notificationResult: notificationResults) {
            Notification notification = new Notification(notificationResult.getText(), notificationResult.getDateTime().toLocalDateTime());
            notifications.add(notification);
        }
        return renderData(notifications);
    }

    /**
     * Metodo che restituisce una lista di tutte le raccolte dell'utente loggato memorizzate nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedPersonalCollections() {
        ArrayList<CollectionResultInterface> collectionResults = collectionDAO.getUserPersonalCollections(loggedUser.getUsername());
        return renderCollections(collectionResults);
    }

    /**
     * Metodo che restituisce una lista di tutte le raccolte salvate dall'utente loggato memorizzate nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedSavedCollections() {
        ArrayList<CollectionResultInterface> collectionResults = collectionDAO.getUserSavedCollections(loggedUser.getUsername());
        return renderCollections(collectionResults);
    }

    /**
     * Subroutine utilizzata per renderizzare le raccolte dell'applicativo
     * @param collectionResults
     */
    private ArrayList<Map<String, Object>> renderCollections(ArrayList<CollectionResultInterface> collectionResults) {
        ArrayList<AbstractModel> collections = new ArrayList<>();
        for (CollectionResultInterface collectionResult: collectionResults) {
            Collection collection = new Collection(collectionResult.getId(), collectionResult.getName(), collectionResult.getOwner(), Collection.Visibility.valueOf(collectionResult.getVisibility().toUpperCase()));
            collections.add(collection);
        }
        return renderData(collections);
    }

    /**
     * Metodo che restituisce una lista di tutti i libri memorizzati nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedBooks() {
        ArrayList<BookResultInterface> bookResults = bookDAO.getAll();
        return renderBooks(bookResults);
    }

    /**
     * Metodo che restituisce una lista di tutti i libri memorizzati nel database data una modalità fruizione
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedBooksByFruitionMode(Book.FruitionMode fruitionMode) {
        ArrayList<BookResultInterface> bookResults = bookDAO.getBooksByFruitionMode(fruitionMode);
        return renderBooks(bookResults);
    }

    /**
     * Metodo che restituisce una lista di tutti i libri memorizzati nel database dato un editore
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedBooksByPublisher(String publisher) {
        ArrayList<BookResultInterface> bookResults = bookDAO.getBooksByPublisher(publisher);
        return renderBooks(bookResults);
    }

    /**
     * Subroutine che renderizza i libri
     * @param bookResults
     */
    private ArrayList<Map<String, Object>> renderBooks(ArrayList<BookResultInterface> bookResults) {
        ArrayList<AbstractModel> books = new ArrayList<>();
        for (BookResultInterface bookResult: bookResults) {
            Book book = new Book(bookResult.getIsbn(), bookResult.getTitle(), bookResult.getPublisher(), Book.FruitionMode.valueOf(bookResult.getFruitionMode().toUpperCase()), bookResult.getPublicationYear(), null, bookResult.getDescription(), Book.BookType.valueOf(bookResult.getBookType().toUpperCase()), bookResult.getGenre(), bookResult.getTarget(), bookResult.getTopic());
            books.add(book);
        }
        return renderData(books);
    }

    /**
     * Metodo che elimina un libro dal database
     * @param isbn
     */
    public boolean removeBookFromDatabase(String isbn) {
        return bookDAO.deleteBookByIsbn(isbn);
    }

    /**
     * Metodo che modifica un libro dal database
     * @param bookToUpdate
     * @param isbn
     * @param title
     * @param publisher
     * @param fruitionMode
     * @param publicationYear
     * @param description
     * @param genre
     * @param target
     * @param topic
     * @param type
     * @throws Exception
     */
    public Map<String, Object> updateBookFromDatabase(String bookToUpdate, String isbn, String title, String publisher, String fruitionMode, int publicationYear, String description, String genre, String target, String topic, String type) throws Exception {
        BookResultInterface bookResult = bookDAO.updateBookByIsbn(bookToUpdate, isbn, title, publisher, Book.FruitionMode.valueOf(fruitionMode.toUpperCase()), publicationYear, null, description, genre, target, topic, Book.BookType.valueOf(type.toUpperCase()));
        if (bookResult != null) {
            return new Book(bookResult.getIsbn(), bookResult.getTitle(), bookResult.getPublisher(), Book.FruitionMode.valueOf(bookResult.getFruitionMode().toUpperCase()), bookResult.getPublicationYear(), null, bookResult.getDescription(), Book.BookType.valueOf(bookResult.getBookType().toUpperCase()), bookResult.getGenre(), null, null).getData();
        }
        return null;
    }

    /**
     * Metodo che aggiorna un articolo scientifico dal database
     * @param publicationToUpdate
     * @param doi
     * @param title
     * @param publisher
     * @param fruitionMode
     * @param publicationYear
     * @param description
     */
    public Map<String, Object> updateScientificPublicationFromDatabase(String publicationToUpdate, String doi, String title, String publisher, String fruitionMode, int publicationYear, String description) throws Exception {
        ScientificPublicationResultInterface scientificPublicationResult = publicationDAO.updateScientificPublicationByDoi(publicationToUpdate, doi, title, publisher, ScientificPublication.FruitionMode.valueOf(fruitionMode.toUpperCase()), publicationYear, description);
        if (scientificPublicationResult != null) {
            return new ScientificPublication(scientificPublicationResult.getDoi(), scientificPublicationResult.getTitle(), ScientificPublication.FruitionMode.valueOf(scientificPublicationResult.getFruitionMode().toUpperCase()), publicationYear, null, description, scientificPublicationResult.getPublisher()).getData();
        }
        return null;
    }

    /**
     * Metodo che aggiorna un autore dal database
     * @param id
     * @param name
     * @param birthDate
     * @param deathDate
     * @param nationality
     * @param biografia
     */
    public Map<String, Object> updateAuthorFromDatabase(int id, String name, LocalDate birthDate, LocalDate deathDate, String nationality, String biografia) throws Exception {
        Date parsedBirthdate = birthDate != null ? Date.valueOf(birthDate) : null;
        Date parsedDeathDate = deathDate != null ? Date.valueOf(deathDate) : null;
        AuthorResultInterface resultSet = authorDAO.updateAuthorById(id, name, parsedBirthdate, parsedDeathDate, nationality, biografia);
        if (resultSet != null) {
            LocalDate parsedLocalBirthdate = resultSet.getBirthDate() != null ? resultSet.getBirthDate().toLocalDate() : null;
            LocalDate parsedLocalDeathDate = resultSet.getDeathDate() != null ? resultSet.getDeathDate().toLocalDate() : null;
            return new Author(resultSet.getId(), resultSet.getName(), parsedLocalBirthdate, parsedLocalDeathDate, resultSet.getNationality(), resultSet.getBio()).getData();
        }
        return null;
    }

    /**
     * Metodo che aggiorna un negozio dal database
     * @param storeToUpdate
     * @param partitaIva
     * @param name
     * @param address
     * @param url
     */
    public Map<String, Object> updateStoreFromDatabase(String storeToUpdate, String partitaIva, String name, String address, String url) throws Exception {
        StoreResultInterface resultSet = storeDAO.updateStoreByPartitaIva(storeToUpdate, partitaIva, name, address, url);
        if (resultSet != null) {
            return new Store(resultSet.getPartitaIva(), resultSet.getName(), resultSet.getAddress(), resultSet.getUrl()).getData();
        }
        return null;
    }

    /**
     * Metodo che aggiorna una collana nel database
     * @param editorialCollectionToUpdate
     * @param issn
     * @param name
     * @param publisher
     */
    public Map<String, Object> updateEditorialCollectionFromDatabase(String editorialCollectionToUpdate, String issn, String name, String publisher) throws Exception{
        EditorialCollectionResultInterface resultSet = editorialCollectionDAO.updateEditorialCollectionByIssn(editorialCollectionToUpdate, issn, name, publisher);
        if (resultSet != null) {
            return new EditorialCollection(resultSet.getIssn(), resultSet.getName(), resultSet.getPublisher()).getData();
        }
        return null;
    }

    /**
     * Metodo che aggiorna una rivista nel database
     * @param jorunalToUpdate
     * @param issn
     * @param name
     * @param argument
     * @param publicationYear
     * @param manager
     * @throws Exception
     */
    public Map<String, Object> updateJournalFromDatabase(String jorunalToUpdate, String issn, String name, String argument, int publicationYear, String manager) throws Exception {
        JournalResultInterface resultSet = journalDAO.updateJournalByIssn(jorunalToUpdate, issn, name, argument, publicationYear, manager);
        if (resultSet != null) {
            return new Journal(resultSet.getIssn(), resultSet.getName(), resultSet.getArgument(), resultSet.getPublicationYear(), resultSet.getManager()).getData();
        }
        return null;
    }

    /**
     * Metodo che aggiorna una conferenza nel database
     * @param conferenceId
     * @param location
     * @param startDate
     * @param endDate
     * @param organizer
     * @param manager
     * @throws Exception
     */
    public Map<String, Object> updateConferenceFromDatabase(int conferenceId, String location, LocalDate startDate, LocalDate endDate, String organizer, String manager) throws Exception {
        Date parsedStartDate = startDate != null ? Date.valueOf(startDate) : null;
        Date parsedEndDate = endDate != null ? Date.valueOf(endDate) : null;
        ConferenceResultInterface resultSet = conferenceDAO.updateConferenceById(conferenceId, location, parsedStartDate, parsedEndDate, organizer, manager);
        if (resultSet != null) {
            LocalDate parsedLocalStartDate = resultSet.getStartDate() != null ? resultSet.getStartDate().toLocalDate() : null;
            LocalDate parsedLocalEndDate = resultSet.getEndDate() != null ? resultSet.getEndDate().toLocalDate() : null;
            return new Conference(resultSet.getId(), resultSet.getPlace(), parsedLocalStartDate, parsedLocalEndDate, resultSet.getOrganizer(), resultSet.getManager()).getData();
        }
        return null;
    }

    /**
     * Metodo che aggiorna una sala/libreria nel database
     * @param id
     * @param name
     * @param address
     * @throws Exception
     */
    public Map<String, Object> updatePresentationHallFromDatabase(int id, String name, String address) throws Exception{
        PresentationHallResultInterface resultSet = presentationHallDAO.updatePresentationHallById(id, name, address);
        if (resultSet != null) {
            return new PresentationHall(resultSet.getId(), resultSet.getName(), resultSet.getAddress()).getData();
        }
        return null;
    }

    /**
     * Metodo che restituisce una lista di tutti gli articoli scientifici memorizzati nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedScientificPublications() {
        ArrayList<ScientificPublicationResultInterface> scientificPublicationResults = publicationDAO.getAll();
        ArrayList<AbstractModel> scientificPublications = new ArrayList<>();
        for (ScientificPublicationResultInterface scientificPublicationResult: scientificPublicationResults) {
            ScientificPublication scientificPublication = new ScientificPublication(scientificPublicationResult.getDoi(), scientificPublicationResult.getTitle(), ScientificPublication.FruitionMode.valueOf(scientificPublicationResult.getFruitionMode().toUpperCase()), scientificPublicationResult.getPublicationYear(), null, scientificPublicationResult.getDescription(), scientificPublicationResult.getPublisher());
            scientificPublications.add(scientificPublication);
        }
        return renderData(scientificPublications);
    }

    /**
     * Metodo che elimina un articolo scientifico dal database
     * @param doi
     */
    public boolean removeScientificPublicationFromDatabase(String doi) {
        return publicationDAO.deleteScientificPublicationByDoi(doi);
    }

    /**
     * Metodo che restituisce una lista di tutti gli autori memorizzati nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedAuthors() {
        ArrayList<AuthorResultInterface> authorResults = authorDAO.getAll();
        ArrayList<AbstractModel> authors = new ArrayList<>();
        for (AuthorResultInterface authorResult: authorResults) {
            LocalDate deathDate = authorResult.getDeathDate() != null ? authorResult.getDeathDate().toLocalDate() : null;
            Author author = new Author(authorResult.getId(), authorResult.getName(), authorResult.getBirthDate().toLocalDate(), deathDate, authorResult.getNationality(), authorResult.getBio());
            authors.add(author);
        }
        return renderData(authors);
    }

    /**
     * Metodo che elimina un autore dal database
     * @param id
     */
    public boolean removeAuthorFromDatabase(int id) {
        return authorDAO.deleteAuthorById(id);
    }

    /**
     * Metodo che restituisce una lista di tutti i negozi memorizzati nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedStores() {
        ArrayList<StoreResultInterface> storeResults = storeDAO.getAll();
        ArrayList<AbstractModel> stores = new ArrayList<>();
        for (StoreResultInterface storeResult: storeResults) {
            Store store = new Store(storeResult.getPartitaIva(), storeResult.getName(), storeResult.getAddress(), storeResult.getUrl());
            stores.add(store);
        }
        return renderData(stores);
    }

    /**
     * Metodo che elimina un negozio dal database
     * @param partitaIva
     */
    public boolean removeStoreFromDatabase(String partitaIva) {
        return storeDAO.deleteStoreByPartitaIva(partitaIva);
    }

    /**
     * Metodo che restituisce una lista di tutte le collane memorizzate nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedEditorialCollections() {
        ArrayList<EditorialCollectionResultInterface> editorialCollectionResults = editorialCollectionDAO.getAll();
        ArrayList<AbstractModel> editorialCollections = new ArrayList<>();
        for (EditorialCollectionResultInterface editorialCollectionResult: editorialCollectionResults) {
            EditorialCollection editorialCollection = new EditorialCollection(editorialCollectionResult.getIssn(), editorialCollectionResult.getName(), editorialCollectionResult.getPublisher());
            editorialCollections.add(editorialCollection);
        }
        return renderData(editorialCollections);
    }

    /**
     * Metodo che elimina un negozio dal database
     * @param issn
     */
    public boolean removeEditorialCollectionFromDatabase(String issn) {
        return editorialCollectionDAO.deleteEditorialCollectonByIssn(issn);
    }

    /**
     * Metodo che restituisce una lista di tutte le serie memorizzate nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedSeries() {
        ArrayList<SerieResultInterface> serieResults = serieDAO.getAll();
        ArrayList<AbstractModel> series = new ArrayList<>();
        for (SerieResultInterface serieResult: serieResults) {
            Serie serie = new Serie(serieResult.getName(), serieResult.getPrequel(), serieResult.getSequel());
            series.add(serie);
        }
        return renderData(series);
    }

    /**
     * Metodo che elimina un libro da una serie dal database
     * @param prequel
     */
    public boolean removeSerieFromDatabase(String prequel) {
        return serieDAO.deleteSerie(prequel);
    }

    /**
     * Metodo che restituisce una lista di tutte le riviste memorizzate nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedJournals() {
        ArrayList<JournalResultInterface> journalResults = journalDAO.getAll();
        ArrayList<AbstractModel> journals = new ArrayList<>();
        for (JournalResultInterface journalResult: journalResults) {
            Journal journal = new Journal(journalResult.getIssn(), journalResult.getName(), journalResult.getArgument(), journalResult.getPublicationYear(), journalResult.getManager());
            journals.add(journal);
        }
        return renderData(journals);
    }

    /**
     * Metodo che elimina una rivista dal database
     * @param issn
     */
    public boolean removeJournalFromDatabase(String issn) {
        return journalDAO.deleteJournalByIssn(issn);
    }

    /**
     * Metodo che restituisce una lista di tutte le conferenze memorizzate nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedConferences() {
        ArrayList<ConferenceResultInterface> conferenceResults = conferenceDAO.getAll();
        ArrayList<AbstractModel> conferences = new ArrayList<>();
        for (ConferenceResultInterface conferenceResult: conferenceResults) {
            Conference conference = new Conference(conferenceResult.getId(), conferenceResult.getPlace(), conferenceResult.getStartDate().toLocalDate(), conferenceResult.getEndDate().toLocalDate(), conferenceResult.getOrganizer(), conferenceResult.getManager());
            conferences.add(conference);
        }
        return renderData(conferences);
    }

    /**
     * Metodo che elimina una conferenza dal database
     * @param id
     */
    public boolean removeConferenceFromDatabase(int id) {
        return conferenceDAO.deleteConferenceById(id);
    }

    /**
     * Metodo che restituisce una lista di tutte le sale memorizzate nel database
     * renderizzati come ArrayList di coppia chiave/valore
     */
    public ArrayList<Map<String, Object>> getRenderedPresentationHalls() {
        ArrayList<PresentationHallResultInterface> presentationHallResults = presentationHallDAO.getAll();
        ArrayList<AbstractModel> presentationHalls = new ArrayList<>();
        for (PresentationHallResultInterface presentationHallResult: presentationHallResults) {
            PresentationHall presentationHall = new PresentationHall(presentationHallResult.getId(), presentationHallResult.getName(), presentationHallResult.getAddress());
            presentationHalls.add(presentationHall);
        }
        return renderData(presentationHalls);
    }

    /**
     * Metodo che elimina una sala dal database
     * @param id
     */
    public boolean removePresentationHallFromDatabase(int id) {
        return presentationHallDAO.deletePresentationHallById(id);
    }

    /**
     * Metodo che restituisce una lista di tutti gli articoli pubblicati da una rivista memorizzata nel database
     * @param issn
     */
    public ArrayList<Map<String, Object>> getScientificPublicationsFromJournal(String issn) {
        ArrayList<ScientificPublicationResultInterface> resultSets = journalDAO.getPublicationsFromJournal(issn);
        ArrayList<AbstractModel> scientificPublications = new ArrayList<>();
        for (ScientificPublicationResultInterface resultSet: resultSets) {
            scientificPublications.add(new ScientificPublication(resultSet.getDoi(), resultSet.getTitle(), ScientificPublication.FruitionMode.valueOf(resultSet.getFruitionMode().toUpperCase()), resultSet.getPublicationYear(), null, resultSet.getDescription(), resultSet.getPublisher()));
        }
        return renderData(scientificPublications);
    }

    /**
     * Metodo che restituisce una lista di tutti i libri contenuti in una raccolta memorizzata nel database
     * @param issn
     */
    public ArrayList<Map<String, Object>> getBooksFromEditorialCollection(String issn) {
        ArrayList<BookResultInterface> resultSets = editorialCollectionDAO.getBooksFromEditorialCollection(issn);
        return renderBooks(resultSets);
    }

    /**
     * Metodo che restituisce una lista di tutte le presentazioni di libri effettuate in una libreria memorizzata nel database
     * @param presentationHallId
     */
    public ArrayList<Map<String, Object>> getBookPresentationsFromPresentationHall(int presentationHallId) {
        ArrayList<BookPresentationResultInterface> resultSets = presentationHallDAO.getBookPresentations(presentationHallId);
        ArrayList<AbstractModel> bookPresentations = new ArrayList<>();
        for (BookPresentationResultInterface resultSet: resultSets) {
            LocalDate parsedPresentationDate = resultSet.getPresentationDate() != null ? resultSet.getPresentationDate().toLocalDate() : null;
            bookPresentations.add(new BookPresentation(
                new PresentationHall(resultSet.getPresentationHall().getId(), resultSet.getPresentationHall().getName(), resultSet.getPresentationHall().getAddress()),
                new Book(resultSet.getPresentedBook().getIsbn(), resultSet.getPresentedBook().getTitle(), resultSet.getPresentedBook().getPublisher(), Book.FruitionMode.valueOf(resultSet.getPresentedBook().getFruitionMode().toUpperCase()), resultSet.getPresentedBook().getPublicationYear(), null, resultSet.getPresentedBook().getDescription(), Book.BookType.valueOf(resultSet.getPresentedBook().getBookType().toUpperCase()), resultSet.getPresentedBook().getGenre(), resultSet.getPresentedBook().getTarget(), resultSet.getPresentedBook().getTopic()),
                parsedPresentationDate
            ));
        }
        return renderData(bookPresentations);
    }

    /**
     * Metodo che restituisce una lista di tutte le presentazioni di articoli scientifici effettuate durante una conferenza memorizzata nel database
     * @param conferenceId
     */
    public ArrayList<Map<String, Object>> getScientificPublicationPresentationsFromConference(int conferenceId) {
        ArrayList<ScientificPublicationPresentationResultInterface> resultSets = conferenceDAO.getScientificPublicatonPresentations(conferenceId);
        ArrayList<AbstractModel> scientificPublicationPresentations = new ArrayList<>();
        for (ScientificPublicationPresentationResultInterface resultSet: resultSets) {
            LocalDate parsedPresentationDate = resultSet.getPresentationDate() != null ? resultSet.getPresentationDate().toLocalDate() : null;
            LocalDate parsedStartDate = resultSet.getConference().getStartDate() != null ? resultSet.getConference().getStartDate().toLocalDate() : null;
            LocalDate parsedEndDate = resultSet.getConference().getEndDate() != null ? resultSet.getConference().getEndDate().toLocalDate() : null;
            scientificPublicationPresentations.add(new ScientificPublicationPresentation(
                    new Conference(resultSet.getConference().getId(), resultSet.getConference().getPlace(), parsedStartDate, parsedEndDate, resultSet.getConference().getOrganizer(), resultSet.getConference().getManager()),
                    new ScientificPublication(resultSet.getPresentedScientificPublication().getDoi(), resultSet.getPresentedScientificPublication().getTitle(), ScientificPublication.FruitionMode.valueOf(resultSet.getPresentedScientificPublication().getFruitionMode().toUpperCase()), resultSet.getPresentedScientificPublication().getPublicationYear(), null, resultSet.getPresentedScientificPublication().getDescription(), resultSet.getPresentedScientificPublication().getPublisher()),
                    parsedPresentationDate
            ));
        }
        return renderData(scientificPublicationPresentations);
    }

    /**
     * Metodo che restituisce una lista di tutti gli autori di un libro memorizzato nel database
     * @param isbn
     */
    public ArrayList<Map<String, Object>> getAuthorsOfBook(String isbn) {
        ArrayList<AuthorResultInterface> resultSets = bookDAO.getAuthorsOfBook(isbn);
        return renderAuthors(resultSets);
    }

    /**
     * Metodo che restituisce una lista di tutti gli autori di un articolo scientifico memorizzato nel database
     * @param doi
     */
    public ArrayList<Map<String, Object>> getAuthorsOfScientificPublication(String doi) {
        ArrayList<AuthorResultInterface> resultSets = publicationDAO.getAuthorsOfScientificPublication(doi);
        return renderAuthors(resultSets);
    }

    /**
     * Subroutine che renderizza gli autori di libri o di articoli scientifici
     * @param resultSets
     */
    private ArrayList<Map<String, Object>> renderAuthors(ArrayList<AuthorResultInterface> resultSets) {
        ArrayList<AbstractModel> authors = new ArrayList<>();
        for (AuthorResultInterface resultSet: resultSets) {
            LocalDate parsedLocalBirthdate = resultSet.getBirthDate() != null ? resultSet.getBirthDate().toLocalDate() : null;
            LocalDate parsedLocalDeathDate = resultSet.getDeathDate() != null ? resultSet.getDeathDate().toLocalDate() : null;
            authors.add(new Author(resultSet.getId(), resultSet.getName(), parsedLocalBirthdate, parsedLocalDeathDate, resultSet.getNationality(), resultSet.getBio()));
        }
        return renderData(authors);
    }

    /**
     * Metodo che restituisce una lista di tutte le vendite di libri effettuate da un negozio memorizzato nel database
     * @param partitaIva
     */
    public ArrayList<Map<String, Object>> getBookSalesFromStore(String partitaIva) {
        ArrayList<BookSaleResultInterface> resultSets = storeDAO.getBookSales(partitaIva);
        ArrayList<AbstractModel> bookSales = new ArrayList<>();
        for (BookSaleResultInterface resultSet: resultSets) {
            bookSales.add(new BookSale(
                    new Store(resultSet.getStore().getPartitaIva(), resultSet.getStore().getName(), resultSet.getStore().getAddress(), resultSet.getStore().getUrl()),
                    new Book(resultSet.getBook().getIsbn(), resultSet.getBook().getTitle(), resultSet.getBook().getPublisher(), Book.FruitionMode.valueOf(resultSet.getBook().getFruitionMode().toUpperCase()), resultSet.getBook().getPublicationYear(), null, resultSet.getBook().getDescription(), Book.BookType.valueOf(resultSet.getBook().getBookType().toUpperCase()), resultSet.getBook().getGenre(), resultSet.getBook().getTarget(), resultSet.getBook().getTopic()),
                    resultSet.getPrice(),
                    resultSet.getQuantity()
            ));
        }
        return renderData(bookSales);
    }

    /**
     * Metodo che mostra a video tutti gli articoli pubblicati da una rivista
     * @param issn
     */
    public void showScientificPublicationsInJournal(String issn) {
        JournalResultInterface journalResult = journalDAO.getJournalByIssn(issn);
        Journal journal = new Journal(journalResult.getIssn(), journalResult.getName(), journalResult.getArgument(), journalResult.getPublicationYear(), journalResult.getManager());
        switchView(new ScientificPublicationsInJournalGUI(this, journal.getData(), getScientificPublicationsFromJournal(issn), getRenderedScientificPublications(), getCurrentView()));
    }

    /**
     * Metodo che mostra a video tutti i libri presenti in una collana
     * @param issn
     */
    public void showBooksInEditorialCollection(String issn) {
        EditorialCollectionResultInterface editorialCollectionResult = editorialCollectionDAO.getEditorialCollectionByIssn(issn);
        EditorialCollection editorialCollection = new EditorialCollection(editorialCollectionResult.getIssn(), editorialCollectionResult.getName(), editorialCollectionResult.getPublisher());
        switchView(new BooksInEditorialCollectionGUI(this, editorialCollection.getData(), getBooksFromEditorialCollection(issn), getRenderedBooksByPublisher(editorialCollection.getPublisher()), getCurrentView()));
    }

    /**
     * Metodo che mostra a video tutti i libri presenti in una libreria
     * @param presentationHallId
     */
    public void showBookPresentations(int presentationHallId) {
        PresentationHallResultInterface presentationHallResult = presentationHallDAO.getPresentationhallById(presentationHallId);
        PresentationHall presentationHall = new PresentationHall(presentationHallResult.getId(), presentationHallResult.getName(), presentationHallResult.getAddress());
        switchView(new BookPresentationsGUI(this, presentationHall.getData(), getBookPresentationsFromPresentationHall(presentationHallId), getRenderedBooks(), getCurrentView()));
    }

    /**
     * Metodo che mostra a video tutti gli articoli scientifici presentati in una conferenza
     * @param conferenceId
     */
    public void showScientificPublicationPresentations(int conferenceId) {
        ConferenceResultInterface conferenceResult = conferenceDAO.getConferenceById(conferenceId);
        Conference conference = new Conference(conferenceResult.getId(), conferenceResult.getPlace(), conferenceResult.getStartDate().toLocalDate(), conferenceResult.getEndDate().toLocalDate(), conferenceResult.getOrganizer(), conferenceResult.getManager());
        switchView(new ScientificPublicationsPresentationsGUI(this, conference.getData(), getScientificPublicationPresentationsFromConference(conferenceId), getRenderedScientificPublications(), getCurrentView()));
    }

    /**
     * Metodo che mostra a video tutti gli autori di un libro
     * @param isbn
     */
    public void showAuthorsOfBook(String isbn) {
        BookResultInterface bookResult = bookDAO.getBookByIsbn(isbn);
        Book book = new Book(bookResult.getIsbn(), bookResult.getTitle(), bookResult.getPublisher(), Book.FruitionMode.valueOf(bookResult.getFruitionMode().toUpperCase()), bookResult.getPublicationYear(), null, bookResult.getDescription(), Book.BookType.valueOf(bookResult.getBookType().toUpperCase()), bookResult.getGenre(), bookResult.getTarget(), bookResult.getTopic());
        switchView(new AuthorsOfBookGUI(this, book.getData(), getAuthorsOfBook(isbn), getRenderedAuthors()));
    }

    /**
     * Metodo che mostra a video tutti gli autori di un articoo scientifico
     * @param doi
     */
    public void showAuthorsOfScientificPublication(String doi) {
        ScientificPublicationResultInterface scientificPublicationResult = publicationDAO.getScientificPublicationByDoi(doi);
        ScientificPublication scientificPublication = new ScientificPublication(scientificPublicationResult.getDoi(), scientificPublicationResult.getTitle(), ScientificPublication.FruitionMode.valueOf(scientificPublicationResult.getFruitionMode().toUpperCase()), scientificPublicationResult.getPublicationYear(), null, scientificPublicationResult.getDescription(), scientificPublicationResult.getPublisher());
        switchView(new AuthorsOfScientificPublicationGUI(this, scientificPublication.getData(), getAuthorsOfScientificPublication(doi), getRenderedAuthors(), getCurrentView()));
    }

    /**
     * Metodo che mostra a video tutti i libri venduti da un negozio
     * @param partitaIva
     */
    public void showBookSales(String partitaIva) {
        StoreResultInterface storeResult = storeDAO.getStoreByPartitaIva(partitaIva);
        Store store = new Store(storeResult.getPartitaIva(), storeResult.getName(), storeResult.getAddress(), storeResult.getUrl());
        ArrayList<Map<String, Object>> renderedBooks;
        if (!storeResult.getAddress().isBlank() && store.getUrl().isBlank()) {
            renderedBooks = getRenderedBooksByFruitionMode(Book.FruitionMode.valueOf("CARTACEO"));
        } else {
            renderedBooks = getRenderedBooks();
        }
        switchView(new BookSalesGUI(this, store.getData(), getBookSalesFromStore(partitaIva), renderedBooks, getCurrentView()));
    }

    /**
     * Metodo che aggiorna le relazioni fra articoli scientifici e riviste
     * @param issn
     * @param doi
     * @param isSelected
     */
    public void updateScientificPublicationsFromJournal(String issn, String doi, boolean isSelected) {
        if (isSelected) {
            journalDAO.insertScientificPublicationIntoJournal(issn, doi);
        } else {
            journalDAO.deleteScientificPublicationFromJournal(issn, doi);
        }
    }

    /**
     *  Metodo che aggiorna le relazioni fra libri e collane
     * @param issn
     * @param isbn
     * @param isSelected
     */
    public void updateBooksFromEditorialCollection(String issn, String isbn, boolean isSelected) {
        if (isSelected) {
            editorialCollectionDAO.insertBookIntoEditorialCollection(issn, isbn);
        } else {
            editorialCollectionDAO.deleteBookFromEditorialCollection(issn, isbn);
        }
    }

    /**
     * Metodo che aggiorna le relazioni fra libri e librerie
     * @param isbn
     * @param presentationHallId
     * @param presentationDate
     * @param isSelected
     */
    public void updateBookPresentation(String isbn, int presentationHallId, LocalDate presentationDate, boolean isSelected) throws Exception {
        if (isSelected) {
            Date parsedPresentationDate = presentationDate != null ? Date.valueOf(presentationDate) : null;
            presentationHallDAO.insertBookIntoPresentationHall(isbn, presentationHallId, parsedPresentationDate);
        } else {
            presentationHallDAO.deleteBookFromPresentationHall(isbn, presentationHallId);
        }
    }

    /**
     * Metodo che aggiorna le relazioni fra articoli e conferenze
     * @param doi
     * @param conferenceId
     * @param presentationDate
     * @param isSelected
     * @throws Exception
     */
    public void updateScientificPublicationPresentation(String doi, int conferenceId, LocalDate presentationDate, boolean isSelected) throws Exception {
        if (isSelected) {
            Date parsedPresentationDate = presentationDate != null ? Date.valueOf(presentationDate) : null;
            conferenceDAO.insertScientificPublicationPresentation(conferenceId, doi, parsedPresentationDate);
        } else {
            conferenceDAO.deleteScientificPublicationPresentation(conferenceId, doi);
        }
    }

    /**
     * Metodo che aggiorna le relazioni fra libri e autori
     * @param authorId
     * @param isbn
     * @param isSelected
     */
    public void updateAuthorsOfBook(int authorId, String isbn, boolean isSelected) {
        if (isSelected) {
            bookDAO.insertAuthorOfBook(authorId, isbn);
        } else {
            bookDAO.deleteAuthorOfBook(authorId, isbn);
        }
    }

    /**
     * Metodo che aggiorna le relazioni fra articoli scientifici e autori
     * @param authorId
     * @param doi
     * @param isSelected
     */
    public void updateAuthorsOfScientificPublication(int authorId, String doi, boolean isSelected) {
        if (isSelected) {
            publicationDAO.insertAuthorOfScientificPublication(authorId, doi);
        } else {
            publicationDAO.deleteAuthorOfScientificPublication(authorId, doi);
        }
    }
}