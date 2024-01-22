package PostgresImplementationDAO;

import DAO.JournalDAOInterface;
import DAO.JournalResultInterface;
import DAO.ScientificPublicationResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JournalDAO implements JournalDAOInterface {
    @Override
    public JournalResultInterface getJournalByIssn(String issn) {
        final String query = "SELECT * FROM Rivista WHERE issn = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, issn);
            statement.execute();

            ResultSet result = statement.getResultSet();
            if (result.next()) {
                return new JournalResult(result);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<JournalResultInterface> getAll() {
        final String query = "SELECT * FROM Rivista";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<JournalResultInterface> journalResults = new ArrayList<>();
            while(result.next()){
                JournalResultInterface journalResult = new JournalResult(result);
                journalResults.add(journalResult);
            }
            return journalResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteJournalByIssn(String issn) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM rivista WHERE issn = ?");
        ) {
            statement.setString(1, issn);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<ScientificPublicationResultInterface> getPublicationsFromJournal(String issn) {
        final String query = "SELECT a.doi, a.titolo, a.editore, a.modalita_fruizione, a.anno_pubblicazione, a.copertina, a.descrizione FROM articolo_scientifico_pubblicazione_rivista AS ar " +
                "JOIN articolo_scientifico AS a ON a.doi = ar.articolo_scientifico " +
                "WHERE ar.rivista = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, issn);
            ResultSet result = statement.executeQuery();

            ArrayList<ScientificPublicationResultInterface> scientificPublicationResults = new ArrayList<>();
            while (result.next()) {
                ScientificPublicationResultInterface scientificPublicationResult = new ScientificPublicationResult(result);
                scientificPublicationResults.add(scientificPublicationResult);
            }
            return scientificPublicationResults;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean insertScientificPublicationIntoJournal(String scientificPublication, String journal) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO articolo_scientifico_pubblicazione_rivista VALUES (?,?)");
        ) {
            statement.setString(1, scientificPublication);
            statement.setString(2, journal);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteScientificPublicationFromJournal(String scientificPublication, String journal) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM articolo_scientifico_pubblicazione_rivista WHERE articolo_scientifico = ? AND rivista = ?");
        ) {
            statement.setString(1, scientificPublication);
            statement.setString(2, journal);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public JournalResultInterface insertJournalInDb(String issn, String name, String argument, int publication_year, String manager) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Rivista VALUES (?, NULLIF(?, ''), NULLIF(?, ''), ?, NULLIF(?, '')) RETURNING Rivista.*");
        ) {
            statement.setString(1, issn);
            statement.setString(2, name);
            statement.setString(3, argument);
            statement.setInt(4, publication_year);
            statement.setString(5, manager);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new JournalResult(result);
        } catch (SQLException e) {
            System.out.println("ERRORE:" + e.getMessage());
            if (e.getMessage().contains("rivista_pk"))
                throw new Exception("Stai cercando di inserire una rivista con un issn che esiste gia'.");
            else if (e.getMessage().contains("issn_check"))
                throw new Exception("Violazione del vincolo di formato per issn di una rivista.");
            else if (e.getMessage().contains("nome") && e.getMessage().contains("null value"))
                throw new Exception("Il nome non puo' essere nullo.");
            else if (e.getMessage().contains("argomento") && e.getMessage().contains("null value"))
                throw new Exception("L'argomento' non puo' essere nullo.");
            else if (e.getMessage().contains("responsabile") && e.getMessage().contains("null value"))
                throw new Exception("Responsabile non puo' essere nullo.");
            else
                throw new Exception("General Error For Journal.");
        }
        //return null;
    }
    }
