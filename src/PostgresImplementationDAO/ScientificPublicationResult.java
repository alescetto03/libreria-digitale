package PostgresImplementationDAO;

import DAO.ScientificPublicationResultInterface;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScientificPublicationResult implements ScientificPublicationResultInterface {
    String doi;
    String title;
    String fruition_mode;
    int publication_year;
    InputStream cover;
    String description;
    String publisher;


    public ScientificPublicationResult(String doi, String title, String publisher, String fruition_mode, int publication_year, InputStream cover, String description) {
        this.doi = doi;
        this.title = title;
        this.fruition_mode = fruition_mode;
        this.publication_year = publication_year;
        this.cover = cover;
        this.description = description;
        this.publisher = publisher;
    }

    public ScientificPublicationResult(ResultSet result) throws SQLException {
        this(
                result.getString("doi"),
                result.getString("titolo"),
                result.getString("editore"),
                result.getString("modalita_fruizione"),
                result.getInt("anno_pubblicazione"),
                result.getBinaryStream("copertina"),
                result.getString("descrizione")
        );
    }

    public String getDoi() {
        return doi;
    }

    public String getTitle() {
        return title;
    }

    public String getFruition_mode() {
        return fruition_mode;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public InputStream getCover() {
        return cover;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }

}
