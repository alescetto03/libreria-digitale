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

    @Override
    public boolean deleteEditorialCollectonByIssn(String issn) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM collana WHERE issn = ?");
        ) {
            statement.setString(1, issn);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public EditorialCollectionResultInterface insertEditorialCollectionInDb(String issn, String name, String publisher) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Collana VALUES (?, NULLIF(?, ''), NULLIF(?, '')) RETURNING Collana.*");
        ) {
            statement.setString(1, issn);
            statement.setString(2, name);
            statement.setString(3, publisher);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new EditorialCollectionResult(result);
        } catch (SQLException e) {
            //System.out.println(e.getMessage() + " " + e.getSQLState());
            if (e.getMessage().contains("issn_check"))
                throw new Exception("Violazione dei vincolo di formato di ISSN per una collana.");
            else if (e.getMessage().contains("collana_pk"))
                throw new Exception("Stai cercando di inserire una collana con un issn gia' esistente.");
            else if (e.getMessage().contains("nome") && e.getMessage().contains("null value"))
                throw new Exception("Il nome non puo' essere nullo.");
            else if (e.getMessage().contains("editore") && e.getMessage().contains("null value"))
                throw new Exception("L'editore non puo' essere nullo.");
            else
                throw new Exception("General Error For Editorial Collection.");
        }
        //return null;
    }
    }
