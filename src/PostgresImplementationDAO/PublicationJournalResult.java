package PostgresImplementationDAO;

import DAO.PublicationJournalResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicationJournalResult implements PublicationJournalResultInterface {
    String doi;
    String issn;
    String publicationTitle;
    String journalName;

    public PublicationJournalResult(String doi, String issn, String publicationTitle, String journalName) {
        this.doi = doi;
        this.issn = issn;
        this.publicationTitle = publicationTitle;
        this.journalName = journalName;
    }

    public PublicationJournalResult(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getString("doi"),
            resultSet.getString("issn"),
            resultSet.getString("titolo_articolo"),
            resultSet.getString("nome_rivista")
        );
    }

    public String getDoi() {
        return doi;
    }

    public String getIssn() {
        return issn;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public String getJournalName() {
        return journalName;
    }
}
