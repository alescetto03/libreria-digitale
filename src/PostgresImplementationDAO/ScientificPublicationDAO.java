package PostgresImplementationDAO;

import DAO.BookResultInterface;
import DAO.ScientificPublicationDAOInterface;
import DAO.ScientificPublicationResultInterface;
import Model.ScientificPublication;

import java.sql.*;
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

    @Override
    public ScientificPublicationResultInterface insertPublicationInDb(String doi, String title, String publisher, ScientificPublication.FruitionMode fruition_mode, int publication_year, byte[] cover, String description) throws Exception {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Articolo_Scientifico VALUES (?, NULLIF(?, ''), NULLIF(?, ''), ?, ?, NULLIF(?, ''), NULLIF(?, '')) RETURNING Articolo_Scientifico.*");
        ) {
            statement.setString(1, doi);
            statement.setString(2, title);
            statement.setString(3, publisher);
            statement.setObject(4, fruition_mode.name().toLowerCase(), Types.OTHER);
            statement.setInt(5, publication_year);
            statement.setBytes(6, cover);
            statement.setString(7, description);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new ScientificPublicationResult(result);
        } catch (SQLException e) {
            //System.out.println(e.getMessage() + " " + e.getSQLState());
//            System.out.println("MESSAGE:" + e.getMessage());
//            System.out.println("SQL STATE:" + e.getSQLState());
//            System.out.println("ERROR CODE:" + e.getErrorCode());
//            System.out.println("LOCALIZED MESSAGE:" + e.getLocalizedMessage());
//            System.out.println("NEXT EXCEPTION:" + e.getNextException());
//            System.out.println("CAUSE:" + e.getCause());
//            System.out.println("STACK TRACE:" + e.getStackTrace());
            if (e.getMessage().contains("doi_check"))
                throw new Exception("Un DOI deve essere una sequenza alfanumerica che inizia con '10.' e non puo' essere nulla.");
            else if (e.getMessage().contains("articolo_scientifico_pk"))
                throw new Exception("Stai inserendo un articolo con un doi gia' esistente.");
            else if (e.getMessage().contains("titolo") && e.getMessage().contains("null value"))
                throw new Exception("Il titolo non puo' essere nullo.");
            else if (e.getMessage().contains("editore") && e.getMessage().contains("null value"))
                throw new Exception("L'editore non puo' essere nullo.");
            else
                throw new Exception("General Error For Publication");
        }
        //return null;
    }
}
