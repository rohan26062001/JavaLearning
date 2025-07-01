package statements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*; // Step 1 : Importing Packages
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCStatements {

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");   // Step 2 and 3 : Load and Register the Driver Jar

        List<String> connectionProperties = loadConnectionProperties();

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
        printResultSet(statement.executeQuery("SELECT * FROM student"));

        // Read
        System.out.println("Output after READ Operation: ");
        printResultSet(statement.executeQuery("SELECT * FROM student"));

        // Update
        statement.execute("UPDATE student SET marks = 22 WHERE id = 1;");
        System.out.println("Output after UPDATE Operation: ");
        printResultSet(statement.executeQuery("SELECT * FROM student"));

        // Delete
        statement.execute("DELETE FROM student WHERE id = 3;");
        System.out.println("Output after DELETE Operation: ");
        printResultSet(statement.executeQuery("SELECT * FROM student"));
    }

    private static List<String> loadConnectionProperties() throws Exception {
        List<String> connectionDetails = new ArrayList<>(3);
        Properties properties = new Properties();

        try (InputStream inputStream = JDBCStatements.class.getClassLoader().getResourceAsStream("connection.properties")) {

            if (inputStream == null) {
                throw new FileNotFoundException("Property file 'connection.properties' not found in the classpath");
            }

            properties.load(inputStream);

            // Retrieve properties and add to list
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            connectionDetails.add(url);
            connectionDetails.add(username);
            connectionDetails.add(password);

        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as needed
        }

        return connectionDetails;
    }

    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Print column headers
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%-20s", metaData.getColumnLabel(i));
        }
        System.out.println();

        // Print a separator
        for (int i = 1; i <= columnCount; i++) {
            System.out.print("--------------------");
        }
        System.out.println();

        // Print rows
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s", rs.getString(i));
            }
            System.out.println();
        }
    }
}
