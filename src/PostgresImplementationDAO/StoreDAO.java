package PostgresImplementationDAO;

import DAO.StoreDAOInterface;
import DAO.StoreResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StoreDAO implements StoreDAOInterface {
    @Override
    public ArrayList<StoreResultInterface> storeCompleteSerie(String searchedSerie) {
        final String query = "SELECT * FROM negoziconseriecomplete WHERE nome_serie ILIKE '%' || ? || '%'";
        try(
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, searchedSerie);
            ResultSet result = statement.executeQuery();

            ArrayList<StoreResultInterface> searchedStore = new ArrayList<StoreResultInterface>();
            while(result.next()){
                StoreResult store = new StoreResult(result.getString("partita_iva"), result.getString("nome_negozio"), result.getString("indirizzo"), result.getString("url"));
                searchedStore.add(store);
            }

            return searchedStore;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<StoreResultInterface> getAll() {
        final String query = "SELECT * FROM Negozio";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<StoreResultInterface> storeResults = new ArrayList<>();
            while(result.next()){
                StoreResultInterface storeResult = new StoreResult(result);
                storeResults.add(storeResult);
            }
            return storeResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteStoreByPartitaIva(String partitaIva) {
        try (
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM negozio WHERE partita_iva = ?");
        ) {
            statement.setString(1, partitaIva);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public StoreResultInterface insertStoreInDb(String partita_iva, String name, String address, String url) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Negozio VALUES (?, NULLIF(?, ''), NULLIF(?, ''), NULLIF(?, '')) RETURNING Negozio.*");
        ) {
            statement.setString(1, partita_iva);
            statement.setString(2, name);
            statement.setString(3, address);
            statement.setString(4, url);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new StoreResult(result);
        } catch (SQLException e) {
            //System.out.println(e.getMessage() + " " + e.getSQLState() + " " + e.getErrorCode());
            if (e.getMessage().contains("validita_negozio"))
                throw new Exception("Violazione dl vincolo di validita' di negozio, non possono esserci indirizzo e url nulli.");
            else if (e.getMessage().contains("partita_iva_check"))
                throw new Exception("Violazione dl vincolo di partita iva, formato errato.");
            else if (e.getMessage().contains("negozio_pk"))
                throw new Exception("Stai cercando di inserire un negozio gia' esistente.");
            else if (e.getMessage().contains("nome") && e.getMessage().contains("null value"))
                throw new Exception("Il nome non puo' essere nullo.");
            else
                throw new Exception("General Error For Store.");
        }
        //return null;
    }

    @Override
    public StoreResultInterface updateStore(String partita_iva, String old_partita_iva, String name, String address, String url) throws Exception{
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("UPDATE Negozio SET partita_iva = ?, nome = NULLIF(?, ''), indirizzo = NULLIF(?, ''), url = NULLIF(?, '') WHERE partita_iva = ? RETURNING *");
        ) {
            statement.setString(1, partita_iva);
            statement.setString(2, name);
            statement.setString(3, address);
            statement.setString(4, url);
            statement.setString(5, old_partita_iva);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new StoreResult(result);
        } catch (SQLException e) {
            //System.out.println(e.getMessage() + " " + e.getSQLState() + " " + e.getErrorCode());
            if (e.getMessage().contains("validita_negozio"))
                throw new Exception("Violazione dl vincolo di validita' di negozio, non possono esserci indirizzo e url nulli.");
            else if (e.getMessage().contains("partita_iva_check"))
                throw new Exception("Violazione dl vincolo di partita iva, formato errato.");
            else if (e.getMessage().contains("negozio_pk"))
                throw new Exception("Stai cercando di inserire un negozio gia' esistente.");
            else if (e.getMessage().contains("nome") && e.getMessage().contains("null value"))
                throw new Exception("Il nome non puo' essere nullo.");
            else
                throw new Exception("General Error For Store.");
        }
        //return null;
    }


}
