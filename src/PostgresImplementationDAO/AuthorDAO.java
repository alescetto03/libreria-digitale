package PostgresImplementationDAO;

import DAO.AuthorDAOInterface;
import DAO.AuthorResultInterface;
import DAO.BookResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDAO implements AuthorDAOInterface {
    public ArrayList<AuthorResultInterface> getAll() {
        final String query = "SELECT * FROM Autore";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<AuthorResultInterface> authorResults = new ArrayList<>();
            while(result.next()){
                AuthorResultInterface authorResult = new AuthorResult(result);
                authorResults.add(authorResult);
            }
            return authorResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteAuthorById(int id) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM autore WHERE cod_autore = ?");
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
