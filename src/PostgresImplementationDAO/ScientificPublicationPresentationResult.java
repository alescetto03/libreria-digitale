package PostgresImplementationDAO;

import DAO.ConferenceResultInterface;
import DAO.ScientificPublicationPresentationResultInterface;
import DAO.ScientificPublicationResultInterface;

import java.sql.Date;

public class ScientificPublicationPresentationResult implements ScientificPublicationPresentationResultInterface {
    private ConferenceResultInterface conference;
    private ScientificPublicationResultInterface presentedScientificPublication;
    private Date presentationDate;

    public ScientificPublicationPresentationResult(ConferenceResultInterface conference, ScientificPublicationResultInterface presentedScientificPublication, Date presentationDate) {
        this.conference = conference;
        this.presentedScientificPublication = presentedScientificPublication;
        this.presentationDate = presentationDate;
    }

    @Override
    public ConferenceResultInterface getConference() {
        return conference;
    }

    @Override
    public ScientificPublicationResultInterface getPresentedScientificPublication() {
        return presentedScientificPublication;
    }

    @Override
    public Date getPresentationDate() {
        return presentationDate;
    }
}