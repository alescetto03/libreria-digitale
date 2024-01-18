package PostgresImplementationDAO;

import DAO.EditorialCollectionDAOInterface;
import DAO.EditorialCollectionResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditorialCollectionDAO implements EditorialCollectionDAOInterface {
    @Override
    public ArrayList<EditorialCollectionResultInterface> getAll() {
        final String query = "SELECT * FROM Collana";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<EditorialCollectionResultInterface> editorialCollectionResults = new ArrayList<>();
            while(result.next()){
                EditorialCollectionResultInterface editorialCollectionResult = new EditorialCollectionResult(result);
                editorialCollectionResults.add(editorialCollectionResult);
            }
            return editorialCollectionResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }
}
