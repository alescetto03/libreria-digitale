package PostgresImplementationDAO;

import DAO.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Classe per l'interfacciamente con il database per l'entita' conferenza.
 * Permette di fare tutte le operazioni di base, come reperire, eliminare, inserire ed aggiornare conferenze
 */
public class ConferenceDAO implements ConferenceDAOInterface {
    @Override
    public ConferenceResultInterface getConferenceById(int conferenceId) {
        final String query = "SELECT * FROM Conferenza WHERE cod_conferenza = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, conferenceId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new ConferenceResult(result);
            }
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<ConferenceResultInterface> getAll() {
        final String query = "SELECT * FROM Conferenza ORDER BY cod_conferenza";
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

    @Override
    public ConferenceResultInterface insertConferenceInDb(String location, Date startDate, Date endDate, String organizer, String manager) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Conferenza (luogo, data_inizio, data_fine, organizzatore, responsabile) VALUES (NULLIF(?, ''), ?, ?, NULLIF(?, ''), NULLIF(?, '')) RETURNING Conferenza.*");
        ) {
            statement.setString(1, location);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setString(4, organizer);
            statement.setString(5, manager);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new ConferenceResult(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("luogo") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"luogo\" non può essere vuoto.");
            else if (e.getMessage().contains("organizzatore") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"organizzatore\" non può essere vuoto.");
            else if (e.getMessage().contains("responsabile") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"responsabile\" non può essere vuoto.");
            else if (e.getMessage().contains("data_inizio") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"data di inizio\" non può essere vuoto.");
            else if (e.getMessage().contains("data_fine") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"data di fine\" non può essere vuoto.");
            else if (e.getMessage().contains("Validita_Date"))
                throw new Exception("La data di inizio deve essere precedente a quella di quella di fine.");
            else
                throw new Exception("C'è stato un errore durante l'inserimento");
        }
    }


    @Override
    public ConferenceResultInterface updateConferenceById(int conferenceId, String location, Date startDate, Date endDate, String organizer, String manager) throws Exception {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("UPDATE Conferenza SET luogo = NULLIF(?, ''), data_inizio = ?, data_fine = ?, organizzatore = NULLIF(?, ''), responsabile = NULLIF(?, '') WHERE cod_conferenza = ? RETURNING Conferenza.*");
        ) {
            statement.setString(1, location);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setString(4, organizer);
            statement.setString(5, manager);
            statement.setInt(6, conferenceId);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new ConferenceResult(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("luogo") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"luogo\" non può essere vuoto.");
            else if (e.getMessage().contains("organizzatore") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"organizzatore\" non può essere vuoto.");
            else if (e.getMessage().contains("responsabile") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"responsabile\" non può essere vuoto.");
            else if (e.getMessage().contains("data_inizio") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"data di inizio\" non può essere vuoto.");
            else if (e.getMessage().contains("data_fine") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"data di fine\" non può essere vuoto.");
            else if (e.getMessage().contains("Validita_Date"))
                throw new Exception("La data di inizio deve essere precedente a quella di quella di fine.");
            else
                throw new Exception("C'è stato un errore durante l'inserimento");
        }
    }

    @Override
    public ArrayList<ScientificPublicationPresentationResultInterface> getScientificPublicatonPresentations(int conferenceId) {
        final String query = "SELECT c.cod_conferenza, c.luogo, c.data_inizio, c.data_fine, c.organizzatore, c.responsabile, pa.data_presentazione, a.doi, titolo, editore, modalita_fruizione, anno_pubblicazione, copertina, descrizione FROM presentazione_articolo AS pa " +
                "JOIN articolo_scientifico AS a ON a.doi = pa.articolo_scientifico " +
                "JOIN conferenza c on pa.conferenza = c.cod_conferenza " +
                "WHERE pa.conferenza = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, conferenceId);
            ResultSet result = statement.executeQuery();

            ArrayList<ScientificPublicationPresentationResultInterface> scientificPublicationPresentationResults = new ArrayList<>();
            while (result.next()) {
                ScientificPublicationResultInterface scientificPublicationResult = new ScientificPublicationResult(result);
                ConferenceResultInterface conferenceResult = new ConferenceResult(result);
                ScientificPublicationPresentationResultInterface scientificPublicationPresentationResult = new ScientificPublicationPresentationResult(conferenceResult, scientificPublicationResult, result.getDate("data_presentazione"));
                scientificPublicationPresentationResults.add(scientificPublicationPresentationResult);
            }
            return scientificPublicationPresentationResults;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean insertScientificPublicationPresentation(int conference, String scientificPublication, Date presentationDate) throws Exception {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO presentazione_articolo VALUES (?,?,?) ON CONFLICT (conferenza, articolo_scientifico) DO UPDATE SET data_presentazione = ?");
        ) {
            statement.setInt(1, conference);
            statement.setString(2, scientificPublication);
            statement.setDate(3, presentationDate);
            statement.setDate(4, presentationDate);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getSQLState().equals("23502") && e.getMessage().contains("data_presentazione")) {
                throw new Exception("Inserisci una data di presentazione!");
            }
            return false;
        }
    }

    @Override
    public boolean deleteScientificPublicationPresentation(int conference, String scientificPublication) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM presentazione_articolo WHERE articolo_scientifico = ? AND conferenza = ?");
        ) {
            statement.setString(1, scientificPublication);
            statement.setInt(2, conference);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
