package PostgresImplementationDAO;

import DAO.CollectionDAOInterface;
import DAO.CollectionResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CollectionDAO implements CollectionDAOInterface {

    @Override
    public ArrayList<CollectionResultInterface> getUserPersonalCollections(String username) {
        final String query = "SELECT cod_raccolta, nome, visibilita, proprietario FROM Raccolta WHERE Raccolta.proprietario = ?";
        try (
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            ArrayList<CollectionResultInterface> output = new ArrayList<CollectionResultInterface>();
            while (result.next()) {
                CollectionResult collectionResult = new CollectionResult(result);
                output.add(collectionResult);
            }
            return output;
        } catch (SQLException e) {
            System.out.println("Errore:" + e.getMessage());
        }
        return null;
    }

    public boolean deleteCollectionById(int collectionId) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM raccolta WHERE cod_raccolta = ?");
        ) {
            statement.setInt(1, collectionId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
