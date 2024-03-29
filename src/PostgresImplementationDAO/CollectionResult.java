package PostgresImplementationDAO;

import DAO.CollectionResultInterface;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Raccolta del database
 */
public class CollectionResult implements CollectionResultInterface {
    int id;
    String name;
    String owner;
    String visibility;

    public CollectionResult(int id, String name, String visibility, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.visibility = visibility;
    }

    public CollectionResult(ResultSet result) throws SQLException {
        this(
                result.getInt("cod_raccolta"),
                result.getString("nome"),
                result.getString("visibilita"),
                result.getString("proprietario")
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getVisibility() {
        return visibility;
    }
}
