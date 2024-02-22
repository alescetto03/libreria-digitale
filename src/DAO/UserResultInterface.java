package DAO;

import java.time.LocalDate;

/**
 * Interfaccia che rappresenta una tupla della tabella Utente del database
 */
public interface UserResultInterface {
    String getUsername();
    String getEmail();
    String getName();
    String getSurname();
    LocalDate getBirthdate();
    boolean isAdmin();
}
