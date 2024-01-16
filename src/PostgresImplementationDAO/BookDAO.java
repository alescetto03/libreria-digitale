package PostgresImplementationDAO;

import DAO.BookDAOInterface;
import DAO.BookResultInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO implements BookDAOInterface {
    @Override
    public ArrayList<BookResultInterface> getResearchedBook(String searchedBook){
        final String query = "SELECT * FROM Libro WHERE Libro.titolo ILIKE '%'|| ? ||'%'";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
            ) {
                statement.setString(1, searchedBook);
                ResultSet result = statement.executeQuery();

                ArrayList<BookResultInterface> searchedBookList = new ArrayList<BookResultInterface>();
                while(result.next()){
                    BookResult book = new BookResult(result);
                    searchedBookList.add(book);
                }

                return searchedBookList;
            }catch (SQLException e){
                System.out.println("Errore: " + e.getMessage());
                return null;
        }
    }

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

}