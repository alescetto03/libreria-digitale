package PostgresImplementationDAO;

import DAO.BookResultInterface;
import DAO.ScientificPublicationDAOInterface;
import DAO.ScientificPublicationResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScientificPublicationDAO implements ScientificPublicationDAOInterface {
    @Override
    public ArrayList<ScientificPublicationResultInterface> getResearchedPublication(String searchedPublication) {
        final String query = "SELECT * FROM Articolo_Scientifico WHERE Articolo_Scientifico.titolo ILIKE '%' || ? || '%'";
        try(
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, searchedPublication);
            ResultSet result = statement.executeQuery();

            ArrayList<ScientificPublicationResultInterface> searchedPublicationList = new ArrayList<ScientificPublicationResultInterface>();
            while(result.next()){
                ScientificPublicationResult publication = new ScientificPublicationResult(result);
                searchedPublicationList.add(publication);
            }

            return searchedPublicationList;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<ScientificPublicationResultInterface> getAll() {
        final String query = "SELECT * FROM Articolo_Scientifico";
        try(
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<ScientificPublicationResultInterface> scientificPublicationResults = new ArrayList<>();
            while(result.next()){
                ScientificPublicationResult scientificPublicationResult = new ScientificPublicationResult(result);
                scientificPublicationResults.add(scientificPublicationResult);
            }
            return scientificPublicationResults;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteScientificPublicationByDoi(String doi) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM articolo_scientifico WHERE doi = ?");
        ) {
            statement.setString(1, doi);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
