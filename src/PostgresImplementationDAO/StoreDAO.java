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
        final String query = "SELECT partita_iva, nome_negozio, indirizzo, url FROM negoziconseriecomplete AS n WHERE n.nome_serie ILIKE '%' || ? || '%'";
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
}
