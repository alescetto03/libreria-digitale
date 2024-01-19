package PostgresImplementationDAO;

import DAO.JournalDAOInterface;
import DAO.JournalResultInterface;
import DAO.PublicationJournalResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JournalDAO implements JournalDAOInterface {
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

    public ArrayList<PublicationJournalResultInterface> getPublicationsFromJournal() {
        final String query = "SELECT doi, issn, titolo, nome AS titolo_articolo, nome AS nome_rivista FROM articolo_scientifico_pubblicazione_rivista " +
                "JOIN rivista r ON r.issn = articolo_scientifico_pubblicazione_rivista.rivista " +
                "JOIN articolo_scientifico a ON a.doi = articolo_scientifico_pubblicazione_rivista.articolo_scientifico";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<PublicationJournalResultInterface> publicationJournalResults = new ArrayList<>();
            while (result.next()) {
                PublicationJournalResultInterface publicationJournalResult = new PublicationJournalResult(result);
                publicationJournalResults.add(publicationJournalResult);
            }
            return publicationJournalResults;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }
}
