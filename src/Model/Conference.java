package Model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Conference extends AbstractModel {
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

    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", getId());
        data.put("location", getLocation());
        data.put("start_date", getStartDate());
        data.put("end_date", getEndDate());
        data.put("organizer", getOrganizer());
        data.put("manager", getManager());
        return data;
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

    public LocalDate getStartDate() {
        return start_date;
    }

    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEndDate() {
        return end_date;
    }

    public void setEndDate(LocalDate end_date) {
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
