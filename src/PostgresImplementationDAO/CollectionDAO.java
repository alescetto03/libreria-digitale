package PostgresImplementationDAO;

import DAO.CollectionDAOInterface;
import DAO.CollectionResultInterface;
import DAO.ScientificPublicationResultInterface;
import Model.Collection;

import java.sql.*;
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

    @Override
    public ArrayList<CollectionResultInterface> getUserPersonalCollections(String username) {
        final String query = "SELECT cod_raccolta, nome, visibilita, proprietario FROM Raccolta WHERE Raccolta.proprietario = ? ORDER BY cod_raccolta";
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

    public boolean updateCollectionById(int collectionId, String name, Collection.Visibility visibility, String owner) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("UPDATE raccolta SET nome = ?, visibilita = ?, proprietario = ? WHERE cod_raccolta = ?");
        ) {
            statement.setString(1, name);
            statement.setObject(2, visibility.name().toLowerCase(), Types.OTHER);
            statement.setString(3, owner);
            statement.setInt(4, collectionId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertCollection(String name, Collection.Visibility visibility, String owner) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO raccolta (nome, visibilita, proprietario) VALUES (?, ?, ?)");
        ) {
            statement.setString(1, name);
            statement.setObject(2, visibility.name().toLowerCase(), Types.OTHER);
            statement.setString(3, owner);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
