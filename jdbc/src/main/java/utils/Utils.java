package utils;

import statements.JDBCStatements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Utils {

    public static List<String> loadConnectionProperties() throws Exception {
        List<String> connectionDetails = new ArrayList<>(3);
        Properties properties = new Properties();

        try (InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream("connection.properties")) {

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
