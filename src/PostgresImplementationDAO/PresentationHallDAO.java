package PostgresImplementationDAO;

import DAO.BookResultInterface;
import DAO.PresentationHallDAOInterface;
import DAO.PresentationHallResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PresentationHallDAO implements PresentationHallDAOInterface {
    @Override
    public ArrayList<PresentationHallResultInterface> getAll() {
        final String query = "SELECT * FROM Sala";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<PresentationHallResultInterface> presentationHallResults = new ArrayList<>();
            while(result.next()){
                PresentationHallResultInterface presentationHallResult = new PresentationHallResult(result);
                presentationHallResults.add(presentationHallResult);
            }
            return presentationHallResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deletePresentationHallById(int id) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM sala WHERE cod_sala = ?");
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
