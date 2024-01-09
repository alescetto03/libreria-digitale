package PostgresImplementationDAO;

import DAO.CollectionResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectionResult implements CollectionResultInterface {
    int id;
    String name;
    String owner;
    String visibility;
    public CollectionResult(int id, String name, String owner, String visibility) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.visibility = visibility;
    }

    public CollectionResult(ResultSet results) throws SQLException {
        this(
            results.getInt("cod_raccolta"),
            results.getString("nome"),
            results.getString("proprietario"),
            results.getString("visibilita")
        );
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getVisibility() { return visibility; }
}
