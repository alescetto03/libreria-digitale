package DAO;

import java.sql.Date;

/**
 * Interfaccia che rappresenta una tupla della tabella Autore del database
 */
public interface AuthorResultInterface {
    int getId();

    String getName();

    Date getBirthDate();

    Date getDeathDate();

    String getNationality();

    String getBio();
}
