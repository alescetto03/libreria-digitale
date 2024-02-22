package PostgresImplementationDAO;

import DAO.JournalResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Rivista del database
 */
public class JournalResult implements JournalResultInterface {
    private String issn;
    private String name;
    private String argument;
    private int publicationYear;
    private String manager;

    public JournalResult(String issn, String name, String argument, int publicationYear, String manager) {
        this.issn = issn;
        this.name = name;
        this.argument = argument;
        this.publicationYear = publicationYear;
        this.manager = manager;
    }
    public JournalResult(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getString("issn"),
            resultSet.getString("nome"),
            resultSet.getString("argomento"),
            resultSet.getInt("anno_pubblicazione"),
            resultSet.getString("responsabile")
        );
    }
    public String getIssn() {
        return issn;
    }

    public String getName() {
        return name;
    }

    public String getArgument() {
        return argument;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getManager() {
        return manager;
    }
}
