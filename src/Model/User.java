package Model;

import java.time.LocalDate;

public class User {
    private String username;
    private String email;
    private String name;
    private String surname;
    private LocalDate birth_date;
    private boolean admin;


    public User(String username, String email, String name, String surname, LocalDate birth_date, boolean admin) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birth_date = birth_date;
        this.admin = admin;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
