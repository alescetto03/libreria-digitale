package PostgresImplementationDAO;

import DAO.EditorialCollectionResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Collana del database
 */
public class EditorialCollectionResult implements EditorialCollectionResultInterface {
    private String issn;
    private String name;
    private String publisher;
    public EditorialCollectionResult(String issn, String name, String publisher) {
        this.issn = issn;
        this.name = name;
        this.publisher = publisher;
    }
    public EditorialCollectionResult(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getString("issn"),
            resultSet.getString("nome"),
            resultSet.getString("editore")
        );
    }
    public String getIssn() { return issn; }

    public String getName() { return name; }

    public String getPublisher() { return publisher; }
}
