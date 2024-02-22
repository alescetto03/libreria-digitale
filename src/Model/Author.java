package Model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe model dell'entità Autore
 */
public class Author extends AbstractModel {
    private int id;
    private String name;
    private LocalDate birth_date;
    private LocalDate death_date;
    private String nationality;
    private String bio;

    public Author(int id, String name, LocalDate birth_date, LocalDate death_date, String nationality, String bio){
        this.id = id;
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

    public LocalDate getBirthDate() {
        return birth_date;
    }

    public void setBirthDate(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public LocalDate getDeathDate() {
        return death_date;
    }

    public void setDeathDate(LocalDate death_date) {
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

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", getId());
        data.put("name", getName());
        data.put("birth_date", getBirthDate());
        data.put("death_date", getDeathDate());
        data.put("nationality", getNationality());
        data.put("bio", getBio());
        return data;
    }
}
