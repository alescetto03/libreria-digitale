package DAO;

import Model.Book;

import java.util.ArrayList;

public interface BookDAOInterface {
    ArrayList<BookResultInterface> getResearchedBook(String searchedBook);

    ArrayList<BookResultInterface> getBooksFromCollection(int collectionId);

    ArrayList<BookResultInterface> getAll();

    boolean deleteBookByIsbn(String isbn);

    BookResultInterface updateBookByIsbn(String isbn, String title, String publisher, Book.FruitionMode fruition_mode, int publication_year, byte[] cover, String description, String genre, String target, String topic, Book.BookType type) throws Exception;
    BookResultInterface insertBookInDb(String isbn, String title, String publisher, Book.FruitionMode fruition_mode, int publication_year, byte[] cover, String description, String genre, String target, String topic, Book.BookType type) throws Exception;
}
