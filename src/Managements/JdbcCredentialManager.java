package Managements;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe che si occupa di prendere le credenziali JDBC salvate all'interno del file config.properties
 */
public class JdbcCredentialManager {
    private static final Properties properties = new Properties();
    private static String jdbcUrl;
    private static String jdbcUsername;
    private static String jdbcPassword;

    private static void loadProperties() {
        String configFile = System.getProperty("user.dir") + "/src/config.properties";
        try (FileInputStream input = new FileInputStream(configFile)) {
            // Caricamento delle proprietà dal file di configurazione
            properties.load(input);

            // Recupero delle informazioni di connessione dal file di proprietà
            jdbcUrl = properties.getProperty("jdbc.url");
            jdbcUsername = properties.getProperty("jdbc.username");
            jdbcPassword = properties.getProperty("jdbc.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getJdbcUrl() {
        if (jdbcUrl == null) {
            loadProperties();
        }
        return jdbcUrl;
    }
    public static String getJdbcUsername() {
        if (jdbcUsername == null) {
            loadProperties();
        }
        return jdbcUsername;
    }
    public static String getJdbcPassword() {
        if (jdbcPassword == null) {
            loadProperties();
        }
        return jdbcPassword;
    }
}
