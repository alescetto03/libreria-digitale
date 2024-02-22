package PostgresImplementationDAO;

import DAO.SerieResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Serie del database
 */
public class SerieResult implements SerieResultInterface {
    String name;
    String prequel;
    String sequel;

    public SerieResult(String name, String prequel, String sequel) {
        this.name = name;
        this.prequel = prequel;
        this.sequel = sequel;
    }

    public SerieResult(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getString("nome"),
            resultSet.getString("prequel"),
            resultSet.getString("sequel")
        );
    }

    public String getName() {
        return name;
    }

    public String getPrequel() {
        return prequel;
    }

    public String getSequel() {
        return sequel;
    }
}
