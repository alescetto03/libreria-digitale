package PostgresImplementationDAO;

import DAO.BookPresentationResultInterface;
import DAO.BookResultInterface;
import DAO.PresentationHallResultInterface;

import java.sql.Date;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Presentazione Libro del database
 */
public class BookPresentationResult implements BookPresentationResultInterface {
    private PresentationHallResultInterface presentationHall;
    private BookResultInterface presentedBook;
    private Date presentationDate;

    public BookPresentationResult(PresentationHallResultInterface presentationHall, BookResultInterface presentedBook, Date presentationDate) {
        this.presentationHall = presentationHall;
        this.presentedBook = presentedBook;
        this.presentationDate = presentationDate;
    }

    @Override
    public PresentationHallResultInterface getPresentationHall() {
        return presentationHall;
    }

    @Override
    public BookResultInterface getPresentedBook() {
        return presentedBook;
    }

    @Override
    public Date getPresentationDate() {
        return presentationDate;
    }
}
