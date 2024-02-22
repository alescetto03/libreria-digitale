package PostgresImplementationDAO;

import DAO.*;
import Model.Collection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementazione per PostgreSQL dell'interfaccia DAO che gestisce la tabella Raccolta all'interno del database.
 */
public class CollectionDAO implements CollectionDAOInterface {

    /**
     * @inheritDoc
     * @param searchedCollection
     * @param username
     * @return
     */
    @Override
    public ArrayList<CollectionResultInterface> getReasearchedCollection(String searchedCollection, String username) {
        final String query = "SELECT * FROM Raccolta WHERE Raccolta.visibilita = 'pubblica' AND proprietario <> ? AND Raccolta.nome ILIKE '%' || ? || '%'";
        try(
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, username);
            statement.setString(2, searchedCollection);
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

    /**
     * @inheritDoc
     * @param username
     * @return
     */
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

    /**
     * @inheritDoc
     * @param username
     * @return
     */
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

    /**
     * @inheritDoc
     * @param collectionId
     * @return
     */
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

    /**
     * @inheritDoc
     * @param collectionId
     * @param username
     * @return
     */
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

    /**
     * @inheritDoc
     * @param collectionId
     * @param name
     * @param visibility
     * @param owner
     * @return
     */
    public CollectionResultInterface updateCollectionById(int collectionId, String name, Collection.Visibility visibility, String owner) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("UPDATE raccolta SET nome = NULLIF(?, ''), visibilita = ?, proprietario = ? WHERE cod_raccolta = ? RETURNING Raccolta.*");
        ) {
            statement.setString(1, name);
            statement.setObject(2, visibility.name().toLowerCase(), Types.OTHER);
            statement.setString(3, owner);
            statement.setInt(4, collectionId);
            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new CollectionResult(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @inheritDoc
     * @param name
     * @param visibility
     * @param owner
     * @return
     */
    @Override
    public CollectionResultInterface insertCollection(String name, Collection.Visibility visibility, String owner) {
        if (name.matches("\\s*")) {
            name = null;
        }
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Raccolta (nome, visibilita, proprietario) VALUES (COALESCE(?, 'nuova_raccolta'), ?, ?) RETURNING Raccolta.*");
        ) {
            statement.setString(1, name);
            statement.setObject(2, visibility.name().toLowerCase(), Types.OTHER);
            statement.setString(3, owner);
            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new CollectionResult(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " " + e.getSQLState());
        }
        return null;
    }

    /**
     * @inheritDoc
     * @param collectionId
     * @return
     */
    @Override
    public CollectionResultInterface getCollectionById(int collectionId) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM Raccolta WHERE cod_raccolta = ?");
        ) {
            statement.setInt(1, collectionId);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new CollectionResult(result);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @inheritDoc
     * @param collectionId
     * @param isbn
     * @return
     */
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

    /**
     * @inheritDoc
     * @param collectionId
     * @param doi
     * @return
     */
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

    /**
     * @inheritDoc
     * @param collectionId
     * @param username
     * @return
     */
    @Override
    public boolean saveCollectionById(int collectionId, String username) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Utente_Salvataggio_Raccolta (utente, raccolta) VALUES (?, ?)");
        ) {
            statement.setString(1, username);
            statement.setInt(2, collectionId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @inheritDoc
     * @param collectionId
     * @param bookIsbn
     * @return
     */
    @Override
    public boolean isBookInCollection(int collectionId, String bookIsbn) {
        try(
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM Libro_Contenuto_Raccolta WHERE libro = ? AND raccolta = ?");
        ){
            statement.setString(1, bookIsbn);
            statement.setInt(2, collectionId);
            ResultSet result = statement.executeQuery();

            return result.next();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @inheritDoc
     * @param collectionId
     * @param bookIsbn
     * @return
     */
    @Override
    public boolean insertBookInCollection(int collectionId, String bookIsbn) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Libro_Contenuto_Raccolta (libro, raccolta) VALUES (?, ?)");
        ) {
            statement.setString(1, bookIsbn);
            statement.setInt(2, collectionId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @inheritDoc
     * @param collectionId
     * @param publicationDoi
     * @return
     */
    @Override
    public boolean isPublicationInCollection(int collectionId, String publicationDoi) {
        try(
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM Articolo_Contenuto_Raccolta WHERE articolo_scientifico = ? AND raccolta = ?");
        ){
            statement.setString(1, publicationDoi);
            statement.setInt(2, collectionId);
            ResultSet result = statement.executeQuery();

            return result.next();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @inheritDoc
     * @param collectionId
     * @param publicationDoi
     * @return
     */
    @Override
    public boolean insertPublicationInCollection(int collectionId, String publicationDoi) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Articolo_Contenuto_Raccolta (articolo_scientifico, raccolta) VALUES (NULLIF(?, ''), ?)");
        ) {
            statement.setString(1, publicationDoi);
            statement.setInt(2, collectionId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
