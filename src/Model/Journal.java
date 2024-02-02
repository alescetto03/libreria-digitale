package Model;

import java.util.HashMap;
import java.util.Map;

public class Journal extends AbstractModel {
    private String issn;
    private String name;
    private String argument;
    private int publication_year;
    private String manager;

    public Journal(String issn, String name, String argument, int publication_year, String manager) {
        this.issn = issn;
        this.name = name;
        this.argument = argument;
        this.publication_year = publication_year;
        this.manager = manager;
    }

    public String getIssn() {
        return issn;
    }

    public void setId(int id) {
        this.issn = issn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public int getPublicationYear() {
        return publication_year;
    }

    public void setPublicationYear(int publication_year) {
        this.publication_year = publication_year;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("issn", getIssn());
        data.put("name", getName());
        data.put("argument", getArgument());
        data.put("publication_year", getPublicationYear());
        data.put("manager", getManager());
        return data;
    }
}
