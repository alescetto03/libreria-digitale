package PostgresImplementationDAO;

import DAO.UserResultInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Implementazione per PostgreSQL dell'interfaccia che rappresenta una tupla della tabella Utente del database
 */
public class UserResult implements UserResultInterface {

    private String username;
    private String email;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private boolean isAdmin;
    public UserResult(String username, String email, String name, String surname, LocalDate birthdate, boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.isAdmin = isAdmin;
    }

    public UserResult(ResultSet result) throws SQLException{
        this(
                result.getString("username"),
                result.getString("email"),
                result.getString("nome"),
                result.getString("cognome"),
                result.getDate("data_nascita").toLocalDate(),
                result.getBoolean("amministratore")
                );
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public LocalDate getBirthdate() {
        return birthdate;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
}
