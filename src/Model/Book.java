package Model;

import java.awt.image.BufferedImage;

public class Book {
    private String isbn;
    private String title;
    private String publisher;
    private enum FruitionMode{
        PAPER,
        DIGITAL,
        AUDIOBOOK
    }
    private FruitionMode fruition_mode;
    private int publication_year;
    private BufferedImage cover;
    private String description;
    private enum BookType {
        ROMANZO,
        DIDATTICO
    }
    private BookType book_type;
    private String genre;
    private String target;
    private String topic;

    public Book(String isbn, String title, String publisher, FruitionMode fruition_mode, int publication_year, BufferedImage cover, String description, BookType book_type, String genre, String target, String topic){
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.fruition_mode = fruition_mode;
        this.publication_year = publication_year;
        this.cover = cover;
        this. description = description;
        this.book_type = book_type;
        this.genre = genre;
        this.target = target;
        this.topic = topic;

    }




    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public FruitionMode getFruition_mode() {
        return fruition_mode;
    }

    public void setFruition_mode(FruitionMode fruition_mode) {
        this.fruition_mode = fruition_mode;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public BufferedImage getCover() {
        return cover;
    }

    public void setCover(BufferedImage cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookType getBook_type() {
        return book_type;
    }

    public void setBook_type(BookType book_type) {
        this.book_type = book_type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


}
