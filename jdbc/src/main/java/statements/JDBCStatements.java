package statements;

import utils.Utils;

import java.sql.*; // Step 1 : Importing Packages
import java.util.List;

public class JDBCStatements {

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");   // Step 2 and 3 : Load and Register the Driver Jar

        List<String> connectionProperties = Utils.loadConnectionProperties();

        String url = connectionProperties.get(0);
        String username = connectionProperties.get(1);
        String password = connectionProperties.get(2);

        Connection connection = DriverManager.getConnection(url, username, password);   // Step 4 : Creating Connection

        Statement statement = connection.createStatement(); // Step 5 : Creating Statement

        doCrudOperations(statement); // Step 6 : Execute Query

        connection.close(); // Step 7 : Close the Connection
    }

    private static void doCrudOperations(Statement statement) throws SQLException {
        // Create
        statement.execute("INSERT INTO student VALUES (3, 'Tua', 97.2)");
        System.out.println("Output after CREATE Operation: ");
        Utils.printResultSet(statement.executeQuery("SELECT * FROM student"));

        // Read
        System.out.println("Output after READ Operation: ");
        Utils.printResultSet(statement.executeQuery("SELECT * FROM student"));

        // Update
        statement.execute("UPDATE student SET marks = 22 WHERE id = 1;");
        System.out.println("Output after UPDATE Operation: ");
        Utils.printResultSet(statement.executeQuery("SELECT * FROM student"));

        // Delete
        statement.execute("DELETE FROM student WHERE id = 3;");
        System.out.println("Output after DELETE Operation: ");
        Utils.printResultSet(statement.executeQuery("SELECT * FROM student"));
    }
}
