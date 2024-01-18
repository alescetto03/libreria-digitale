package Model;


import java.util.HashMap;
import java.util.Map;

public class EditorialCollection extends AbstractModel {
    private String issn;
    private String name;
    private String publisher;

    public EditorialCollection(String issn, String name, String publisher){
        this.issn = issn;
        this.name = name;
        this.publisher = publisher;
    }

    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("issn", getIssn());
        data.put("name", getName());
        data.put("publisher", getPublisher());
        return data;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

}
