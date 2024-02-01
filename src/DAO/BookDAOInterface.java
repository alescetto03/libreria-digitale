package DAO;

import Model.Book;

import java.util.ArrayList;

public interface BookDAOInterface {
    BookResultInterface getBookByIsbn(String isbn);
    ArrayList<BookResultInterface> getResearchedBook(String searchedBook);
    ArrayList<BookResultInterface> getBooksFromCollection(int collectionId);
    ArrayList<BookResultInterface> getAll();
    ArrayList<BookResultInterface> getBooksByFruitionMode(Book.FruitionMode fruitionMode);
    boolean deleteBookByIsbn(String isbn);
    BookResultInterface updateBookByIsbn(String bookToUpdate, String isbn, String title, String publisher, Book.FruitionMode fruition_mode, int publication_year, byte[] cover, String description, String genre, String target, String topic, Book.BookType type) throws Exception;
    BookResultInterface insertBookInDb(String isbn, String title, String publisher, Book.FruitionMode fruition_mode, int publication_year, byte[] cover, String description, String genre, String target, String topic, Book.BookType type) throws Exception;
    ArrayList<BookResultInterface> getBooksByPublisher(String publisher);
    ArrayList<AuthorResultInterface> getAuthorsOfBook(String isbn);
    boolean insertAuthorOfBook(int author, String book);
    boolean deleteAuthorOfBook(int author, String book);
}
