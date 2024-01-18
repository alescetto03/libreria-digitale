package PostgresImplementationDAO;

import DAO.JournalDAOInterface;
import DAO.JournalResultInterface;

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
}
