package PostgresImplementationDAO;

import DAO.PresentationHallResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Sala del database
 */
public class PresentationHallResult implements PresentationHallResultInterface {
    int id;
    String name;
    String address;

    public PresentationHallResult(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public PresentationHallResult(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getInt("cod_sala"),
            resultSet.getString("nome"),
            resultSet.getString("indirizzo")
        );
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }
}
