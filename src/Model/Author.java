package Model;

import java.time.LocalDate;

public class Author {
    private int id;
    private String name;
    private LocalDate birth_date;
    private LocalDate death_date;
    private String nationality;
    private String bio;


    public Author(int id, String name, LocalDate birth_date, LocalDate death_date, String nationality, String bio){
        this.name = name;
        this.birth_date = birth_date;
        this.death_date = death_date;
        this.nationality = nationality;
        this.bio = bio;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public LocalDate getDeath_date() {
        return death_date;
    }

    public void setDeath_date(LocalDate death_date) {
        this.death_date = death_date;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
