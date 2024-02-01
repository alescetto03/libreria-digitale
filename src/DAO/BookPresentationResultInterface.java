package DAO;

import java.sql.Date;

public interface BookPresentationResultInterface {
    BookResultInterface getPresentedBook();
    PresentationHallResultInterface getPresentationHall();
    Date getPresentationDate();
}
