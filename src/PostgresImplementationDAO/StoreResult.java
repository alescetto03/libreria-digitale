package PostgresImplementationDAO;

import DAO.StoreResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreResult implements StoreResultInterface {
    String partita_iva;
    String name;
    String address;
    String url;
    public StoreResult(String partita_iva, String name, String address, String url) {
        this.partita_iva = partita_iva;
        this.name = name;
        this.address = address;
        this.url = url;
    }

    public StoreResult(ResultSet result) throws SQLException {
        this(
            result.getString("partita_iva"),
            result.getString("nome"),
            result.getString("indirizzo"),
            result.getString("url")
        );
    }

    public String getPartitaIva() {
        return partita_iva;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }
}
