package PostgresImplementationDAO;

import DAO.AuthorResultInterface;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorResult implements AuthorResultInterface {
    private int id;
    private String name;
    private Date birthDate;
    private Date deathDate;
    private String nationality;
    private String bio;

    public AuthorResult(int id, String name, Date birthDate, Date deathDate, String nationality, String bio) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.nationality = nationality;
        this.bio = bio;
    }

    public AuthorResult(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getInt("cod_autore"),
            resultSet.getString("nome"),
            resultSet.getDate("data_nascita"),
            resultSet.getDate("data_morte"),
            resultSet.getString("nazionalita"),
            resultSet.getString("biografia")
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBio() {
        return bio;
    }
}
