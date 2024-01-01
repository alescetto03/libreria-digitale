package Model;

public class BookSeries {
    private String issn;
    private String name;
    private String publisher;


    public BookSeries(String issn, String name, String publisher){
        this.issn = issn;
        this.name = name;
        this.publisher = publisher;
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
