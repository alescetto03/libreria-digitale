package PostgresImplementationDAO;

import DAO.BookResultInterface;
import DAO.EditorialCollectionDAOInterface;
import DAO.EditorialCollectionResultInterface;

import java.sql.*;
import java.util.ArrayList;

public class EditorialCollectionDAO implements EditorialCollectionDAOInterface {
    @Override
    public EditorialCollectionResultInterface getEditorialCollectionByIssn(String issn) {
        final String query = "SELECT * FROM collana WHERE issn = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, issn);
            statement.execute();

            ResultSet result = statement.getResultSet();
            if (result.next()) {
                return new EditorialCollectionResult(result);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<EditorialCollectionResultInterface> getAll() {
        final String query = "SELECT * FROM Collana ORDER BY issn";
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
            if (e.getMessage().contains("issn_check"))
                throw new Exception("<html>Un ISSN deve essere una sequenza numerica di 8 cifre suddivise in 2 settori da 4 cifre.<br>L’ultima cifra può avere come valore \"X\".</html>");
            else if (e.getMessage().contains("collana_pk"))
                throw new Exception("Esiste già una collana con quell'ISSN!");
            else if (e.getMessage().contains("nome") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"nome\" non può essere vuoto");
            else if (e.getMessage().contains("editore") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"editore\" non può essere vuoto.");
            else
                throw new Exception("C'è stato un errore durante l'inserimento");
        }
    }

    @Override
    public ArrayList<BookResultInterface> getBooksFromEditorialCollection(String issn) {
        final String query = "SELECT l.isbn, l.titolo, l.editore, l.modalita_fruizione, l.anno_pubblicazione, l.copertina, l.descrizione, l.genere, l.target, l.materia, l.tipo FROM libro_contenuto_collana AS lc " +
                "JOIN libro AS l ON l.isbn = lc.libro " +
                "WHERE lc.collana = ?";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, issn);
            ResultSet result = statement.executeQuery();

            ArrayList<BookResultInterface> bookResults = new ArrayList<>();
            while (result.next()) {
                BookResultInterface booksResult = new BookResult(result);
                bookResults.add(booksResult);
            }
            return bookResults;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean insertBookIntoEditorialCollection(String book, String editorialCollection) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO libro_contenuto_collana VALUES (?,?)");
        ) {
            statement.setString(1, book);
            statement.setString(2, editorialCollection);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteBookFromEditorialCollection(String book, String editorialCollection) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM libro_contenuto_collana WHERE libro = ? AND collana = ?");
        ) {
            statement.setString(1, book);
            statement.setString(2, editorialCollection);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    @Override
    public EditorialCollectionResultInterface updateEditorialCollectionByIssn(String editorialCollectionToUpdate, String issn, String name, String publisher) throws Exception {
        final String query = "UPDATE Collana SET issn = ?, nome = NULLIF(?, ''), editore = NULLIF(?, '') WHERE issn = ? RETURNING Collana.*";
        try(
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, issn);
            statement.setString(2, name);
            statement.setString(3, publisher);
            statement.setString(4, editorialCollectionToUpdate);
            statement.execute();
            ResultSet result = statement.getResultSet();

            if (result.next()) {
                return new EditorialCollectionResult(result);
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            if (e.getMessage().contains("issn_check"))
                throw new Exception("<html>Un ISSN deve essere una sequenza numerica di 8 cifre suddivise in 2 settori da 4 cifre.<br>L’ultima cifra può avere come valore \"X\".</html>");
            else if (e.getMessage().contains("collana_pk"))
                throw new Exception("Esiste già una collana con quell'ISSN!");
            else if (e.getMessage().contains("nome") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"nome\" non può essere vuoto.");
            else if (e.getMessage().contains("editore") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"editore\" non può essere vuoto.");
            else if (e.getSQLState().equals("P0001"))
                throw new Exception("Non si può modificare l'editore di una collana non vuota.");
            else
                throw new Exception("C'è stato un errore durante la modifica");
        }
        return null;
    }



}
