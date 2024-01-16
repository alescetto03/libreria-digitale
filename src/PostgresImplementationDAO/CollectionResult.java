package PostgresImplementationDAO;

import DAO.CollectionResultInterface;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

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
