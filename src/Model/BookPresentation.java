package Model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe model dell'entità Presentazione Libro
 */
public class BookPresentation extends AbstractModel {
    private PresentationHall presentationHall;
    private Book presentedBook;
    private LocalDate presentationDate;

    public BookPresentation(PresentationHall presentationHall, Book presentedBook, LocalDate presentationDate) {
        this.presentedBook = presentedBook;
        this.presentationHall = presentationHall;
        this.presentationDate = presentationDate;
    }

    public PresentationHall getPresentationHall() {
        return presentationHall;
    }

    public void setPresentationHall(PresentationHall presentationHall) {
        this.presentationHall = presentationHall;
    }

    public Book getPresentedBook() {
        return presentedBook;
    }

    public void setPresentedBook(Book presentedBook) {
        this.presentedBook = presentedBook;
    }

    public LocalDate getPresentationDate() {
        return presentationDate;
    }

    public void setPresentationDate(LocalDate presentationDate) {
        this.presentationDate = presentationDate;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("presentation_hall", getPresentationHall().getData());
        data.put("presented_book", getPresentedBook().getData());
        data.put("presentation_date", getPresentationDate());
        return data;
    }
}
