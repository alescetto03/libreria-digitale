package DAO;

import java.util.ArrayList;

public interface SerieDAOInterface {
    ArrayList<String> getResearchedSeries(String searchedSeries);
    ArrayList<SerieResultInterface> getAll();
}
