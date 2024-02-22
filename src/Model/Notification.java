package Model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe model dell'entità Notifica
 */
public class Notification extends AbstractModel{
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

    public LocalDateTime getDateTime() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("text", getText());
        data.put("timestamp", getDateTime());
        return data;
    }
}
