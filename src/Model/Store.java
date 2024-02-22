package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe model dell'entità Negozio
 */
public class Store extends AbstractModel{
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


    public String getPartitaIva() {
        return partita_iva;
    }

    public void setPartitaIva(String partita_iva) {
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

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("partita_iva", getPartitaIva());
        data.put("name", getName());
        data.put("address", getAddress());
        data.put("url", getUrl());
        return data;
    }
}
