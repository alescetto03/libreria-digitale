package Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PresentationHall extends AbstractModel {
    private int id;
    private String name;
    private String address;

    public PresentationHall(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", getId());
        data.put("name", getName());
        data.put("address", getAddress());
        return data;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
