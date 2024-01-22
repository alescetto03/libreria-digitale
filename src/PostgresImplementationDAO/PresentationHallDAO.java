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

    @Override
    public PresentationHallResultInterface insertPresentationHallInDb(String name, String address) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Sala (nome, indirizzo) VALUES (NULLIF(?, ''), NULLIF(?, '')) RETURNING Sala.*");
        ) {
            statement.setString(1, name);
            statement.setString(2, address);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new PresentationHallResult(result);
        } catch (SQLException e) {
            //System.out.println(e.getMessage() + " " + e.getSQLState());
            if (e.getMessage().contains("sala_pk"))
                throw new Exception("Stai inserendo una sala che esiste gia'.");
            else if (e.getMessage().contains("nome") && e.getMessage().contains("null value"))
                throw new Exception("Il nome non puo' essere nullo.");
            else if (e.getMessage().contains("indirizzo") && e.getMessage().contains("null value"))
                throw new Exception("L'indirizzo non puo' essere nullo.");
            else
                throw new Exception("General Error For Presentation Hall.");
        }
        //return null;
    }
    }
