package Model;

public class Store {
    private String partita_iva;
    private String name;
    private String address;
    private String url;


    public Store(String partita_iva, String name, String address, String url) {
        this.partita_iva = partita_iva;
        this.name = name;
        this.address = address;
        this.url = url;
    }


    public String getPartita_iva() {
        return partita_iva;
    }

    public void setPartita_iva(String partita_iva) {
        this.partita_iva = partita_iva;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
