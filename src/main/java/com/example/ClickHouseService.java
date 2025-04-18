package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class ClickHouseService {
    private String host;
    private String port;
    private String database;
    private String user;
    private String jwtToken;

    // Constructor that takes JWT token instead of password
    public ClickHouseService(String host, String port, String database, String user, String jwtToken) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.jwtToken = jwtToken;
    }

    // Connects to ClickHouse using the provided parameters and JWT token
    public Connection connect() throws SQLException {
        String url = "jdbc:clickhouse://" + host + ":" + port + "/" + database;

        // Set properties for connection, using JWT token for password
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", jwtToken);  // Use the JWT token here

        return DriverManager.getConnection(url, properties);  // Return the connection
    }

    // Lists the available tables in ClickHouse
    public void listTables() {
        try (Connection conn = connect()) {
            ResultSet rs = conn.createStatement().executeQuery("SHOW TABLES");
            System.out.println("Available Tables:");
            while (rs.next()) {
                System.out.println("- " + rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tables: " + e.getMessage());
        }
    }
    public void exportToCsv(String tableName, List<String> selectedColumns, String outputFilePath) {
        String query = "SELECT " + String.join(",", selectedColumns) + " FROM " + tableName;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             FileWriter writer = new FileWriter(outputFilePath)) {

            // Write column headers
            writer.append(String.join(",", selectedColumns)).append("\n");

            // Write each row of data
            while (rs.next()) {
                for (int i = 1; i <= selectedColumns.size(); i++) {
                    writer.append(rs.getString(i));
                    if (i < selectedColumns.size()) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }

            System.out.println("✅ Data exported successfully to: " + outputFilePath);

        } catch (SQLException | IOException e) {
            System.err.println("❌ Export error: " + e.getMessage());
        }
    }

    // Import data from CSV file into ClickHouse
    public void importFromCsv(String tableName, List<String> selectedColumns, String csvFilePath) {
        String insertQuery = "INSERT INTO " + tableName + " (" + String.join(",", selectedColumns) + ") VALUES (" +
                "?,".repeat(selectedColumns.size() - 1) + "?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery);
             BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

            String line;
            // Skip header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < selectedColumns.size(); i++) {
                    pstmt.setString(i + 1, values[i].trim());
                }
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("✅ Data imported successfully from: " + csvFilePath);

        } catch (SQLException | IOException e) {
            System.err.println("❌ Import error: " + e.getMessage());
        }
    }
}
