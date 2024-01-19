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
}
