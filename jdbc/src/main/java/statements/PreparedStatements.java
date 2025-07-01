package statements;

import utils.Utils;

import java.sql.*;  // Step 1 : Importing Packages
import java.util.List;

public class PreparedStatements {
    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");   // Step 2 and 3 : Load and Register the Driver Jar

        List<String> connectionProperties = Utils.loadConnectionProperties();

        String url = connectionProperties.get(0);
        String username = connectionProperties.get(1);
        String password = connectionProperties.get(2);

        Connection connection = DriverManager.getConnection(url, username, password);   // Step 4 : Creating Connection

        String insertQuery = "INSERT INTO student VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery); // Step 5 : Creating PreparedStatement
        preparedStatement.setInt(1, 4);
        preparedStatement.setString(2, "Maa");
        preparedStatement.setDouble(3, 79.3);

        preparedStatement.execute();    // Step 6 : Executing Query

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM student");
        Utils.printResultSet(resultSet);

        connection.close(); // Step 7 : Close the Connection
    }
}
