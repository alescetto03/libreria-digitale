package PostgresImplementationDAO;

import DAO.BookResultInterface;
import DAO.SerieDAOInterface;
import DAO.SerieResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SerieDAO implements SerieDAOInterface {
    @Override
    public ArrayList<String> getResearchedSeries(String searchedSeries) {
        final String query = "SELECT DISTINCT Serie.nome FROM Serie WHERE Serie.nome ILIKE '%'|| ? ||'%'";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, searchedSeries);
            ResultSet result = statement.executeQuery();

            ArrayList<String> allSeries = new ArrayList<String>();
            while(result.next()){
                allSeries.add(result.getString("nome"));
            }

            return allSeries;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<SerieResultInterface> getAll() {
        final String query = "SELECT * FROM Serie";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<SerieResultInterface> serieResults = new ArrayList<>();
            while(result.next()){
                SerieResultInterface serieResult = new SerieResult(result);
                serieResults.add(serieResult);
            }
            return serieResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }
}
