package DAO;

import java.util.ArrayList;

public interface SerieDAOInterface {
    ArrayList<String> getResearchedSeries(String searchedSeries);
    ArrayList<SerieResultInterface> getAll();
    boolean deleteSerie(String prequel);

    SerieResultInterface insertSeriesInDb(String prequel, String sequel, String name) throws Exception;
}
