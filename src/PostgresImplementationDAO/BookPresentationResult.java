package PostgresImplementationDAO;

import DAO.BookPresentationResultInterface;
import DAO.BookResultInterface;
import DAO.PresentationHallResultInterface;

import java.sql.Date;

public class BookPresentationResult implements BookPresentationResultInterface {
    PresentationHallResultInterface presentationHall;
    BookResultInterface presentedBook;
    Date presentationDate;

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
