package Model;

import java.util.HashMap;
import java.util.Map;

public class Collection extends AbstractModel{
    private int id;
    private String name;
    private String owner;

    public enum Visibility{
        PUBBLICA,
        PRIVATA
    }
    private Visibility visibility;


    public Collection(int id, String name, String owner, Visibility visibility){
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }


    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", getId());
        data.put("name", getName());
        data.put("owner", getOwner());
        data.put("visibility", getVisibility());
        return data;
    }
}
