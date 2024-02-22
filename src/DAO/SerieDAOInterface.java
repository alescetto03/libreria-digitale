package DAO;

import java.util.ArrayList;

/**
 * Interfaccia DAO che gestisce la tabella Serie all'interno del database.
 */
public interface SerieDAOInterface {
    /**
     * Metodo che cerca e restituisce una serie in base ad una parola chiave contenuta nel titolo
     * @param searchedSeries
     * @return
     */
    ArrayList<String> getResearchedSeries(String searchedSeries);

    /**
     * Metodo che restituisce tutte le serie memorizzate nel database
     * @return
     */
    ArrayList<SerieResultInterface> getAll();

    /**
     * Metodo che rimuove un libro da una serie
     * @param prequel
     * @return
     */
    boolean deleteSerie(String prequel);

    /**
     * Metodo che inserisce un libro in una serie
     * @param prequel
     * @param sequel
     * @param name
     * @return
     * @throws Exception
     */
    SerieResultInterface insertSeriesInDb(String prequel, String sequel, String name) throws Exception;
}
