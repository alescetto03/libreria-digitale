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

    @Override
    public ArrayList<CollectionResultInterface> getUserSavedCollections(String username) {
        final String query = "SELECT r.cod_raccolta, r.nome, r.visibilita, r.proprietario " +
                             "FROM Raccolta AS r JOIN Utente_Salvataggio_Raccolta AS usr ON r.cod_raccolta = usr.raccolta " +
                             "WHERE usr.utente = ?";
        try(
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            ArrayList<CollectionResultInterface> savedCollectionList = new ArrayList<CollectionResultInterface>();
            while (result.next()) {
                CollectionResult collectionResult = new CollectionResult(result);
                savedCollectionList.add(collectionResult);
            }
            return savedCollectionList;
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

    public boolean deleteSavedCollectionById(int collectionId, String username){
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM utente_salvataggio_raccolta WHERE raccolta = ? AND utente = ?");
        ) {
            statement.setInt(1, collectionId);
            statement.setString(2, username);
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

    @Override
    public ArrayList<CollectionResultInterface> getAllByCollectionId(int collectionId) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM Raccolta WHERE cod_raccolta = ?");
        ) {
            statement.setInt(1, collectionId);
            ResultSet result = statement.executeQuery();

            ArrayList<CollectionResultInterface> collectionData = new ArrayList<>();
            while(result.next()){
                CollectionResult collection = new CollectionResult(result);
                collectionData.add(collection);
            }

            return collectionData;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteBookFromCollection(int collectionId, String isbn) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM Libro_Contenuto_Raccolta WHERE raccolta = ? AND libro = ?");
        ) {
            statement.setInt(1, collectionId);
            statement.setString(2, isbn);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePublicationFromCollection(int collectionId, String doi) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM Articolo_Contenuto_Raccolta WHERE raccolta = ? AND articolo_scientifico = ?");
        ) {
            statement.setInt(1, collectionId);
            statement.setString(2, doi);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
