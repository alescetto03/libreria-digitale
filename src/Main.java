import Controller.AppController;

public class Main {
    public static void main(String[] args) { (new AppController()).showLogin(); }

    /**
    public static void main(String[] args)  throws Exception {
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO libro VALUES (?,?)");
        ) {
            statement.setString(1, "01234591");
            statement.setString(2, "ciaooo");
            statement.execute();

            ResultSet result = statement.getResultSet();
        } catch (SQLException e) {
            if (e.getSQLState().contains("isbn_check")) {
                throw new Exception("Un ISBN deve essere una sequenza numerica di 13 o 10 cifre che inizia per \'978\' o per \'979\'");
            }
        }
    }**/
}
