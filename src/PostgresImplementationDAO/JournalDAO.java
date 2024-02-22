package PostgresImplementationDAO;

import DAO.JournalDAOInterface;
import DAO.JournalResultInterface;
import DAO.ScientificPublicationResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementazione per PostgreSQL dell'interfaccia DAO che gestisce la tabella Rivista all'interno del database.
 */
public class JournalDAO implements JournalDAOInterface {
    /**
     * @inheritDoc
     * @param issn
     * @return
     */
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

    /**
     * @inheritDoc
     * @return
     */
    @Override
    public ArrayList<JournalResultInterface> getAll() {
        final String query = "SELECT * FROM Rivista ORDER BY issn";
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

    /**
     * @inheritDoc
     * @param issn
     * @return
     */
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

    /**
     * @inheritDoc
     * @param issn
     * @return
     */
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

    /**
     * @inheritDoc
     * @param scientificPublication
     * @param journal
     * @return
     */
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

    /**
     * @inheritDoc
     * @param scientificPublication
     * @param journal
     * @return
     */
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

    /**
     * @inheritDoc
     * @param issn
     * @param name
     * @param argument
     * @param publicationYear
     * @param manager
     * @return
     * @throws Exception
     */
    @Override
    public JournalResultInterface insertJournalInDb(String issn, String name, String argument, int publicationYear, String manager) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Rivista VALUES (?, NULLIF(?, ''), NULLIF(?, ''), ?, NULLIF(?, '')) RETURNING Rivista.*");
        ) {
            statement.setString(1, issn);
            statement.setString(2, name);
            statement.setString(3, argument);
            statement.setInt(4, publicationYear);
            statement.setString(5, manager);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new JournalResult(result);
        } catch (SQLException e) {
            System.out.println("ERRORE:" + e.getMessage());
            if (e.getMessage().contains("rivista_pk"))
                throw new Exception("Esiste già una rivista con quell'ISSN!");
            else if (e.getMessage().contains("issn_check"))
                throw new Exception("<html>Un ISSN deve essere una sequenza numerica di 8 cifre suddivise in 2 settori da 4 cifre.<br>L’ultima cifra può avere come valore ”X”.</html>");
            else if (e.getMessage().contains("nome") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"nome\" non può essere vuoto");
            else if (e.getMessage().contains("argomento") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"argomento\" non può essere vuoto.");
            else if (e.getMessage().contains("responsabile") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"responsabile\" non può essere vuoto.");
            else
                throw new Exception("C'è stato un errore durante l'inserimento.");
        }
    }

    /**
     * @inheritDoc
     * @param journalToUpdate
     * @param issn
     * @param name
     * @param argument
     * @param publication_year
     * @param manager
     * @return
     * @throws Exception
     */
    @Override
    public JournalResultInterface updateJournalByIssn(String journalToUpdate, String issn, String name, String argument, int publication_year, String manager) throws Exception {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("UPDATE Rivista SET issn = ?, nome = NULLIF(?, ''), argomento = NULLIF(?, ''), anno_pubblicazione = ?, responsabile = NULLIF(?, '') WHERE issn = ? RETURNING Rivista.*");
        ) {
            statement.setString(1, issn);
            statement.setString(2, name);
            statement.setString(3, argument);
            statement.setInt(4, publication_year);
            statement.setString(5, manager);
            statement.setString(6, journalToUpdate);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new JournalResult(result);
        } catch (SQLException e) {
            System.out.println("ERRORE:" + e.getMessage());
            if (e.getMessage().contains("rivista_pk"))
                throw new Exception("Esiste già una rivista con quell'ISSN!");
            else if (e.getMessage().contains("issn_check"))
                throw new Exception("<html>Un ISSN deve essere una sequenza numerica di 8 cifre suddivise in 2 settori da 4 cifre.<br>L’ultima cifra può avere come valore ”X”.</html>");
            else if (e.getMessage().contains("nome") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"nome\" non può essere vuoto");
            else if (e.getMessage().contains("argomento") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"argomento\" non può essere vuoto.");
            else if (e.getMessage().contains("responsabile") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"responsabile\" non può essere vuoto.");
            else
                throw new Exception("C'è stato un errore durante la modifica.");
        }
    }
}
