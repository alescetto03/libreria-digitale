package Model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe model dell'entità Presentazione Articolo Scientifico
 */
public class ScientificPublicationPresentation extends AbstractModel {
    private Conference conference;
    private ScientificPublication presentedScientificPublication;
    private LocalDate presentationDate;

    public ScientificPublicationPresentation(Conference conference, ScientificPublication presentedScientificPublication, LocalDate presentationDate) {
        this.conference = conference;
        this.presentedScientificPublication = presentedScientificPublication;
        this.presentationDate = presentationDate;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public ScientificPublication getPresentedScientificPublication() {
        return presentedScientificPublication;
    }

    public void setPresentedScientificPublication(ScientificPublication presentedScientificPublication) {
        this.presentedScientificPublication = presentedScientificPublication;
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
        data.put("conference", getConference().getData());
        data.put("presented_scientific_publication", getPresentedScientificPublication().getData());
        data.put("presentation_date", getPresentationDate());
        return data;
    }
}
