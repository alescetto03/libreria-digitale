package PostgresImplementationDAO;

import DAO.AuthorResultInterface;
import DAO.ScientificPublicationDAOInterface;
import DAO.ScientificPublicationResultInterface;
import Model.ScientificPublication;

import java.sql.*;
import java.util.ArrayList;

/**
 * Classe per l'interfacciamente con il database per l'entita' articoli_scientifici.
 * Permette di fare tutte le operazioni di base, come reperire, eliminare, inserire ed aggiornare articoli scientifici
 */
public class ScientificPublicationDAO implements ScientificPublicationDAOInterface {
    public ScientificPublicationResultInterface getScientificPublicationByDoi(String doi) {
        final String query = "SELECT * FROM articolo_scientifico WHERE doi = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, doi);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new ScientificPublicationResult(result);
            }
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
        }
        return null;
    }
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
        final String query = "SELECT * FROM Articolo_Scientifico ORDER BY doi";
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
    public ScientificPublicationResultInterface updateScientificPublicationByDoi(String publicationToUpdate, String doi, String title, String publisher, ScientificPublication.FruitionMode fruitionMode, int publicationYear, String description) throws Exception {
        final String query = "UPDATE articolo_scientifico SET doi = ?, titolo = NULLIF(?, ''), editore = NULLIF(?, ''), modalita_fruizione = ?, anno_pubblicazione = ?, descrizione = NULLIF(?, '') WHERE doi = ? RETURNING articolo_scientifico.*";
        try(
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, doi);
            statement.setString(2, title);
            statement.setString(3, publisher);
            statement.setObject(4, fruitionMode.name().toLowerCase(), Types.OTHER);
            statement.setInt(5, publicationYear);
            statement.setString(6, description);
            statement.setString(7, publicationToUpdate);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                return new ScientificPublicationResult(result);
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            if (e.getMessage().contains("articolo_scientifico_pk")) {
                throw new Exception("Esiste già un articolo con quel DOI!");
            } else if (e.getMessage().contains("doi_check")) {
                throw new Exception("<html>Un DOI deve essere una sequenza alfanumerica composta da un prefisso e un<br>" +
                        "suffisso separati da uno slash. Il prefissso di un DOI deve iniziare con \"10.\"<br>" +
                        "La sequenza alfanumerica accetta i caratteri speciali: punto, dash e underscore,<br>" +
                        "purché siano succeduti da una stringa alfanumerica di almeno un carattere.<br><br>" +
                        "Esempio: 10.testo/testo_testo-testo.t</html>");
            } else if (e.getSQLState().equals("23502") && e.getMessage().contains("titolo")) {
                throw new Exception("Il campo \"titolo\" non può essere vuoto");
            } else if (e.getSQLState().equals("23502") && e.getMessage().contains("editore")) {
                throw new Exception("Il campo \"editore\" non può essere vuoto");
            } else {
                throw new Exception("C'è stato un errore durante la modifica");
            }
        }
        return null;
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
            System.out.println(e.getMessage());
            if (e.getMessage().contains("doi_check"))
                throw new Exception("<html>Un DOI deve essere una sequenza alfanumerica composta da un prefisso e un<br>" +
                        "suffisso separati da uno slash. Il prefissso di un DOI deve iniziare con \"10.\"<br>" +
                        "La sequenza alfanumerica accetta i caratteri speciali: punto, dash e underscore,<br>" +
                        "purché siano succeduti da una stringa alfanumerica di almeno un carattere.<br><br>" +
                        "Esempio: 10.testo/testo_testo-testo.t</html>");
            else if (e.getMessage().contains("articolo_scientifico_pk"))
                throw new Exception("Esiste già un articolo con quel DOI!");
            else if (e.getMessage().contains("titolo") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"titolo\" non può essere vuoto");
            else if (e.getMessage().contains("editore") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"editore\" non può essere vuoto");
            else
                throw new Exception("C'è stato un errore durante l'inserimento");
        }
    }

    public ArrayList<AuthorResultInterface> getAuthorsOfScientificPublication(String doi) {
        try (
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT cod_autore, nome, data_nascita, data_morte, nazionalita, biografia " +
                                                                    "FROM autore JOIN scrittura_articolo sa on autore.cod_autore = sa.autore " +
                                                                    "WHERE articolo_scientifico = ?");
        ) {
            statement.setString(1, doi);
            ResultSet result = statement.executeQuery();

            ArrayList<AuthorResultInterface> authorResults = new ArrayList<>();
            while (result.next()) {
                AuthorResultInterface authorResult = new AuthorResult(result);
                authorResults.add(authorResult);
            }
            return authorResults;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    public boolean insertAuthorOfScientificPublication(int author, String scientificPublication) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO scrittura_articolo VALUES (?,?)");
        ) {
            statement.setInt(1, author);
            statement.setString(2, scientificPublication);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteAuthorOfScientificPublication(int author, String scientificPublication) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM scrittura_articolo WHERE autore = ? AND articolo_scientifico = ?");
        ) {
            statement.setInt(1, author);
            statement.setString(2, scientificPublication);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
