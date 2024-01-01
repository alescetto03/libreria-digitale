package Model;

import java.time.LocalDate;

public class Conference {
    private int id;
    private String location;
    private LocalDate start_date;
    private LocalDate end_date;
    private String organizer;
    private String manager;


    public Conference(int id, String location, LocalDate start_date, LocalDate end_date, String organizer, String manager) {
        this.id = id;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.organizer = organizer;
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

}
