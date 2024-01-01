package Model;

import java.security.PublicKey;

public class Collection {
    private int id;
    private String name;
    private User owner;
    private enum Visibility{
        PRIVATE,
        PUBLIC
    }
    private Visibility visibility;


    public Collection(int id, String name, User owner, Visibility visibility){
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.visibility = visibility;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
