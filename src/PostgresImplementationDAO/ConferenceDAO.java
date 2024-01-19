package PostgresImplementationDAO;

import DAO.ConferenceDAOInterface;
import DAO.ConferenceResultInterface;
import DAO.PublicationConferenceResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConferenceDAO implements ConferenceDAOInterface {
    @Override
    public ArrayList<ConferenceResultInterface> getAll() {
        final String query = "SELECT * FROM Conferenza";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<ConferenceResultInterface> conferenceResults = new ArrayList<>();
            while(result.next()){
                ConferenceResultInterface conferenceResult = new ConferenceResult(result);
                conferenceResults.add(conferenceResult);
            }
            return conferenceResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteConferenceById(int id) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM conferenza WHERE cod_conferenza = ?");
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<PublicationConferenceResultInterface> getBooksFromConferences() {
        final String query = "SELECT doi, conferenza, titolo AS titolo_articolo, luogo, data_inizio, data_fine FROM presentazione_articolo " +
                "JOIN conferenza c ON c.cod_conferenza = presentazione_articolo.conferenza " +
                "JOIN articolo_scientifico a ON a.doi = presentazione_articolo.articolo_scientifico";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<PublicationConferenceResultInterface> publicationConferenceResults = new ArrayList<>();
            while(result.next()){
                PublicationConferenceResultInterface publicationConferenceResult = new PublicationConferenceResult(result);
                publicationConferenceResults.add(publicationConferenceResult);
            }
            return publicationConferenceResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }
}