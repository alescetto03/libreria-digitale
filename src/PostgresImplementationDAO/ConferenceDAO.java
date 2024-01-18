package PostgresImplementationDAO;

import DAO.ConferenceDAOInterface;
import DAO.ConferenceResultInterface;

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
}
