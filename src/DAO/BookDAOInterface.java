package DAO;

import Model.Book;

import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Libro all'interno del database.
 */
public interface BookDAOInterface {
    /**
     * Metodo che restituisce un libro dato il suo isbn
     * @param isbn
     * @return
     */
    BookResultInterface getBookByIsbn(String isbn);

    /**
     * Metodo che cerca e restituisce un libro in base ad una parola chiave contenuta nel titolo
     * @param searchedBook
     * @return
     */
    ArrayList<BookResultInterface> getResearchedBook(String searchedBook);

    /**
     * Metodo che restituisce tutti i libri da una raccolta
     * @param collectionId
     * @return
     */
    ArrayList<BookResultInterface> getBooksFromCollection(int collectionId);

    /**
     * Metodo che restituisce tutti i libri memorizzati nel database
     * @return
     */
    ArrayList<BookResultInterface> getAll();

    /**
     * Metodo che restituisce i libri memorizzati nel database data una modalità di fruizione
     * @param fruitionMode
     * @return
     */
    ArrayList<BookResultInterface> getBooksByFruitionMode(Book.FruitionMode fruitionMode);

    /**
     * Metodo che elimina un libro dal database dato il suo isbn
     * @param isbn
     * @return
     */
    boolean deleteBookByIsbn(String isbn);

    /**
     * Metodo che aggiorna un libro dal database dato il suo isbn
     * @param bookToUpdate
     * @param isbn
     * @param title
     * @param publisher
     * @param fruition_mode
     * @param publication_year
     * @param cover
     * @param description
     * @param genre
     * @param target
     * @param topic
     * @param type
     * @return
     * @throws Exception
     */
    BookResultInterface updateBookByIsbn(String bookToUpdate, String isbn, String title, String publisher, Book.FruitionMode fruition_mode, int publication_year, byte[] cover, String description, String genre, String target, String topic, Book.BookType type) throws Exception;

    /**
     * Metodo che inserisce un libro all'interno del database
     * @param isbn
     * @param title
     * @param publisher
     * @param fruition_mode
     * @param publication_year
     * @param cover
     * @param description
     * @param genre
     * @param target
     * @param topic
     * @param type
     * @return
     * @throws Exception
     */
    BookResultInterface insertBookInDb(String isbn, String title, String publisher, Book.FruitionMode fruition_mode, int publication_year, byte[] cover, String description, String genre, String target, String topic, Book.BookType type) throws Exception;

    /**
     * Metodo che restituisce i libri memorizzati nel database data un editore
     * @param publisher
     * @return
     */
    ArrayList<BookResultInterface> getBooksByPublisher(String publisher);

    /**
     * Metodo che restituisce gli autori di un libro
     * @param isbn
     * @return
     */
    ArrayList<AuthorResultInterface> getAuthorsOfBook(String isbn);

    /**
     * Metodo che inserisce all'interno del database un'istanza della relazione tra libro e autore
     * @param author
     * @param book
     * @return
     */
    boolean insertAuthorOfBook(int author, String book);

    /**
     * Metodo che elimina dal database un'istanza della relazione tra libro e autore
     * @param author
     * @param book
     * @return
     */
    boolean deleteAuthorOfBook(int author, String book);
}
