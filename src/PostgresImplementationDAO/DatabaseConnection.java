package PostgresImplementationDAO;

import Managements.JdbcCredentialManager;

import java.sql.*;

public class DatabaseConnection {
    static DatabaseConnection instance = null;
    Connection connection;
    public DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql:progetto_libreria", "postgres", "192605");
        } catch (SQLException e) {
            System.out.println("Non è stato possibile connettersi \n");
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() { return connection; }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
