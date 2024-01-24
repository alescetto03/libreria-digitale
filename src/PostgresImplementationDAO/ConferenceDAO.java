package PostgresImplementationDAO;

import DAO.ConferenceDAOInterface;
import DAO.ConferenceResultInterface;
import DAO.PublicationConferenceResultInterface;

import java.sql.*;
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

    @Override
    public ConferenceResultInterface insertConferenceInDb(String location, Date start_date, Date end_date, String organizer, String manager) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Conferenza (luogo, data_inizio, data_fine, organizzatore, responsabile) VALUES (NULLIF(?, ''), ?, ?, NULLIF(?, ''), NULLIF(?, '')) RETURNING Conferenza.*");
        ) {
            statement.setString(1, location);
            statement.setDate(2, start_date);
            statement.setDate(3, end_date);
            statement.setString(4, organizer);
            statement.setString(5, manager);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new ConferenceResult(result);
        } catch (SQLException e) {
            if (e.getMessage().contains("conferenza_pk"))
                throw new Exception("Stai inserendo una conferenza che esiste già.");
            else if (e.getMessage().contains("luogo") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"luogo\" non può essere vuoto.");
            else if (e.getMessage().contains("organizzatore") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"organizzatore\" non può essere vuoto.");
            else if (e.getMessage().contains("responsabile") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"responsabile\" non può essere vuoto.");
            else if (e.getMessage().contains("data_inizio") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"data di inizio\" non può essere vuoto.");
            else if (e.getMessage().contains("data_fine") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"data di fine\" non può essere vuoto.");
            else if (e.getMessage().contains("validita_date"))
                throw new Exception("La data di inizio deve essere precedente a quella di quella di fine.");
            else
                throw new Exception("C'è stato un errore durante l'inserimento");
        }
    }
}
