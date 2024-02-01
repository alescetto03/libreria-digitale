package DAO;

import java.sql.Date;

public interface ScientificPublicationPresentationResultInterface {
    ScientificPublicationResultInterface getPresentedScientificPublication();
    ConferenceResultInterface getConference();
    Date getPresentationDate();
}
