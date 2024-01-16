package PostgresImplementationDAO;

import DAO.BookResultInterface;
import DAO.ScientificPublicationDAOInterface;
import DAO.ScientificPublicationResultInterface;
import Model.ScientificPublication;

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

    @Override
    public ArrayList<ScientificPublicationResultInterface> getPublicationsFromCollection(int publicationId) {
        final String query = "SELECT a.doi, a.titolo, a.editore, a.modalita_fruizione, a.anno_pubblicazione, a.copertina, a.descrizione " +
                             "FROM Articolo_Scientifico AS a JOIN Articolo_Contenuto_Raccolta AS ar ON a.doi = ar.articolo_scientifico " +
                             "WHERE ar.raccolta = ?";
        try(
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, publicationId);
            ResultSet result = statement.executeQuery();

            ArrayList<ScientificPublicationResultInterface> publicationInCollection = new ArrayList<ScientificPublicationResultInterface>();
            while(result.next()){
                ScientificPublicationResult publication = new ScientificPublicationResult(result);
                publicationInCollection.add(publication);
            }

            return publicationInCollection;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }
}
