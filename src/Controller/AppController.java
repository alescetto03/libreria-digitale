package Controller;

import DAO.*;
import GUI.*;
import Model.*;
import PostgresImplementationDAO.*;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

public class AppController {
    JFrame currentWindow;
    UserDAOInterface userDAO = new UserDAO();

    NotificationDAOInterface notificationDAO = new NotificationDAO();

    BookDAOInterface bookDAO = new BookDAO();

    ScientificPublicationDAOInterface publicationDAO = new ScientificPublicationDAO();

    /**
     * Utente correntemente autenticato all'applicativo
     */
    User loggedUser = null;

    ArrayList<Notification> userNotification = new ArrayList<Notification>();

    ArrayList<Book> searchedBook = new ArrayList<Book>();
    ArrayList<ScientificPublication> searchedPublication = new ArrayList<ScientificPublication>();


    public static void main(String[] args) { (new AppController()).showLogin(); }

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

    public void registerUser(String username, String email, String password, String name, String surname, java.util.Date birthdate) {
        // Converti l'oggetto java.util.Date in java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(birthdate.getTime());
        this.userDAO.register(username, email, password.getBytes(StandardCharsets.UTF_8), name, surname, sqlDate);
    }


    public void getCollections() {
    }


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

    public void showSearchResults(String searchText){
        getBookByString(searchText);
        getScientificPublicationByString(searchText);

        ArrayList<AbstractModel> abstractModelsBook = new ArrayList<>(searchedBook);//Effettuo una conversione perché ArrayList<Collection> non è sottotipo di ArrayList<AbstractModel>
        ArrayList<AbstractModel> abstractModelsPublication = new ArrayList<>(searchedPublication);

        ArrayList<Map<String, Object>> renderedSearchedBook = renderData(abstractModelsBook);
        ArrayList<Map<String, Object>> renderedSearchedPublication = renderData(abstractModelsPublication);

        for(Map<String, Object> item : renderedSearchedBook){
            System.out.println(item);
        }
        for(Map<String, Object> item : renderedSearchedPublication){
            System.out.println(item);
        }
    }

    public void showHomePage(){
        getUserNotification();
        ArrayList<AbstractModel> abstractModels = new ArrayList<>(userNotification); //Effettuo una conversione perché ArrayList<Collection> non è sottotipo di ArrayList<AbstractModel>
        ArrayList<Map<String, Object>> renderedUserNotification = renderData(abstractModels);
        switchView(new HomePage(this, renderedUserNotification));
    }
}
