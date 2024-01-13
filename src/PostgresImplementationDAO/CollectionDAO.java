package PostgresImplementationDAO;

import DAO.CollectionDAOInterface;
import DAO.CollectionResultInterface;
import DAO.ScientificPublicationResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CollectionDAO implements CollectionDAOInterface {
    @Override
    public ArrayList<CollectionResultInterface> getReasearchedCollection(String searchedCollection) {
        final String query = "SELECT * FROM Raccolta WHERE Raccolta.visibilita = 'pubblica' AND Raccolta.nome ILIKE '%' || ? || '%'";
        try(
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, searchedCollection);
            ResultSet result = statement.executeQuery();

            ArrayList<CollectionResultInterface> searchedCollectionList = new ArrayList<CollectionResultInterface>();
            while(result.next()){
                CollectionResult collection = new CollectionResult(result);
                searchedCollectionList.add(collection);
            }

            return searchedCollectionList;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }
}
