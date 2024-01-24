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
    public StoreResultInterface updateStoreByPartitaIva(String storeToUpdate, String partitaIva, String name, String address, String url) throws Exception {
        try (
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE negozio SET partita_iva = ?, nome = NULLIF(?, ''), indirizzo = NULLIF(?, ''), url = NULLIF(?, '') WHERE partita_iva = ?");
        ) {
            statement.setString(1, partitaIva);
            statement.setString(2, name);
            statement.setString(3, address);
            statement.setString(4, url);
            statement.setString(5, storeToUpdate);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return new StoreResult(resultSet.getString("partita_iva"), resultSet.getString("nome"), resultSet.getString("indirizzo"), resultSet.getString("url"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("negozio_pk")) {
                throw new Exception("Esiste già un libro con quella partita IVA!");
            } else if (e.getMessage().contains("validita_negozio")) {
                throw new Exception("Un negozio deve possedere un URL o un indirizzo");
            } else if (e.getMessage().contains("partita_iva_check")) {
                throw new Exception(" La \"partita iva\" di un Negozio deve essere una sequenza numerica di 11 cifre");
            } else if (e.getSQLState().equals("23502") && e.getMessage().contains("nome")) {
                throw new Exception("Il campo \"nome\" non può essere vuoto");
            } else {
                throw new Exception("C'è stato un errore durante l'aggiornamento");
            }
        }
        return null;
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
            if (e.getMessage().contains("validita_negozio"))
                throw new Exception("I campi \"indirizzo\" e \"url\" non possono essere entrambi null");
            else if (e.getMessage().contains("partita_iva_check"))
                throw new Exception("La partita IVA di un negozio deve essere una sequenza numerica di 11 cifre.");
            else if (e.getMessage().contains("negozio_pk"))
                throw new Exception("Esiste già un negozio con quella partita IVA!");
            else if (e.getMessage().contains("nome") && e.getSQLState().equals("23502"))
                throw new Exception("Il campo \"nome\" non può essere vuoto");
            else
                throw new Exception("C'è stato un errore durante la modifica");
        }
    }
}
