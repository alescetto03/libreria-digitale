package PostgresImplementationDAO;

import DAO.SerieDAOInterface;
import DAO.SerieResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementazione per PostgreSQL dell'interfaccia DAO che gestisce la tabella Serie all'interno del database.
 */
public class SerieDAO implements SerieDAOInterface {
    /**
     * @inheritDoc
     * @param searchedSeries
     * @return
     */
    @Override
    public ArrayList<String> getResearchedSeries(String searchedSeries) {
        final String query = "SELECT DISTINCT Serie.nome FROM Serie WHERE Serie.nome ILIKE '%'|| ? ||'%'";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, searchedSeries);
            ResultSet result = statement.executeQuery();

            ArrayList<String> allSeries = new ArrayList<String>();
            while(result.next()){
                allSeries.add(result.getString("nome"));
            }

            return allSeries;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    /**
     * @inheritDoc
     * @return
     */
    @Override
    public ArrayList<SerieResultInterface> getAll() {
        final String query = "SELECT * FROM Serie";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet result = statement.executeQuery();

            ArrayList<SerieResultInterface> serieResults = new ArrayList<>();
            while(result.next()){
                SerieResultInterface serieResult = new SerieResult(result);
                serieResults.add(serieResult);
            }
            return serieResults;
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
            return null;
        }
    }

    /**
     * @inheritDoc
     * @param prequel
     * @return
     */
    @Override
    public boolean deleteSerie(String prequel) {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM serie WHERE prequel = ?");
        ) {
            statement.setString(1, prequel);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @inheritDoc
     * @param prequel
     * @param sequel
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public SerieResultInterface insertSeriesInDb(String prequel, String sequel, String name) throws Exception{
        try (
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Serie VALUES (NULLIF(?, ''), NULLIF(?, ''), NULLIF(?, '')) RETURNING Serie.*");
        ) {
            statement.setString(1, name);
            statement.setString(2, prequel);
            statement.setString(3, sequel);

            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            return new SerieResult(result);
        } catch (SQLException e) {
            //System.out.println("MESSAGE:" + e.getMessage());
            if (e.getMessage().contains("serie_pkey") || e.getMessage().contains("serie_sequel_key"))
                throw new Exception("Stai inserendo valori gia' esistent in serie.");
            else if (e.getMessage().contains("prequel_notequal_sequel"))
                throw new Exception("Non puoi inserire un sequel uguale al prequel.");
            else if (e.getMessage().contains("checkOnlyRomanziInSerie"))
                throw new Exception("Non puoi inserire libri che non siano romanzi.");
            else if (e.getMessage().contains("checkFormatoSerie"))
                throw new Exception("Non puoi inserire libri con formato diverso.");
            else if (e.getMessage().contains("checkPartecipazioneSerie"))
                throw new Exception("Non puoi inserire libri che fanno parte di altre serie.");
            else if (e.getMessage().contains("checkCicloSerie"))
                throw new Exception("Stai creando una serie ciclica, questo non e' permesso.");
            else if (e.getMessage().contains("checkSequenzialitaSerie"))
                throw new Exception("Non puoi inserire libri scollegati nella serie.");
            else if (e.getMessage().contains("nome") && e.getMessage().contains("null value"))
                throw new Exception("Il nome non puo' essere nullo.");
            else if (e.getMessage().contains("prequel") && e.getMessage().contains("null value"))
                throw new Exception("Il prequel non puo' essere nullo.");
            else if (e.getMessage().contains("sequel") && e.getMessage().contains("null value"))
                throw new Exception("Il sequel non puo' essere nullo.");
            else
                throw new Exception("General Error For Series.");

        }
    }
}