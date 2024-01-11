package Model;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ScientificPublication extends AbstractModel{
    private String doi;
    private String title;

    public enum FruitionMode{
        cartaceo,
        digitale,
        audiolibro
    }
    private FruitionMode fruition_mode;
    private int publication_year;
    private BufferedImage cover;
    private String description;
    private String publisher;


    public ScientificPublication(String doi, String title, FruitionMode fruition_mode, int publication_year, BufferedImage cover, String description, String publisher) {
        this.doi = doi;
        this.title = title;
        this.fruition_mode = fruition_mode;
        this.publication_year = publication_year;
        this.cover = cover;
        this.description = description;
        this.publisher = publisher;
    }



    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FruitionMode getFruition_mode() {
        return fruition_mode;
    }

    public void setFruition_mode(FruitionMode fruition_mode) {
        this.fruition_mode = fruition_mode;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public BufferedImage getCover() {
        return cover;
    }

    public void setCover(BufferedImage cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("doi", getDoi());
        data.put("title", getTitle());
        data.put("publisher", getPublisher());
        data.put("fruition_mode", getFruition_mode());
        data.put("publication_year", getPublication_year());
        data.put("cover", getCover());
        data.put("description", getDescription());
        return data;
    }

}
