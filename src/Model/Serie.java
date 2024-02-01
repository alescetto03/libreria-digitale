package Model;

import java.util.HashMap;
import java.util.Map;

public class Serie extends AbstractModel {
    private String name;
    private String prequel;
    private String sequel;

    public Serie(String name, String prequel, String sequel) {
        this.name = name;
        this.prequel = prequel;
        this.sequel = sequel;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPrequel() { return prequel; }

    public void setPrequel(String prequel) { this.prequel = prequel; }

    public String getSequel() { return sequel; }

    public void setSequel(String sequel) { this.sequel = sequel; }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", getName());
        data.put("prequel", getPrequel());
        data.put("sequel", getSequel());
        return data;
    }
}