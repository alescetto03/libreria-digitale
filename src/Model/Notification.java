package Model;

import java.time.LocalDateTime;

public class Notification {
    private String text;
    private LocalDateTime date_time;



    public Notification(String text, LocalDateTime date_time) {
        this.text = text;
        this.date_time = date_time;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

}
