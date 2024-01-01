package Model;

public class Megazine {
    private int id;
    private String name;
    private String argument;
    private int publication_year;
    private String manager;


    public Megazine(int id, String name, String argument, int publication_year, String manager) {
        this.id = id;
        this.name = name;
        this.argument = argument;
        this.publication_year = publication_year;
        this.manager = manager;
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

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
