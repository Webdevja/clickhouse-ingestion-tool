package com.example.clickhouse_ingestion_tool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClickHouseService {
    private final String host, port, database, username, jwtToken;

    public ClickHouseService(String host, String port, String database, String username, String jwtToken) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.jwtToken = jwtToken;
    }

    public List<String> listTables() {
        List<String> tables = new ArrayList<>();
        String url = "jdbc:clickhouse://" + host + ":" + port + "/" + database;
        try (Connection conn = DriverManager.getConnection(url, username, jwtToken);
             ResultSet rs = conn.createStatement().executeQuery("SHOW TABLES")) {
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }
    public List<String> listColumns(String tableName) {
        List<String> columns = new ArrayList<>();
        String url = "jdbc:clickhouse://" + host + ":" + port + "/" + database;
        String query = "DESCRIBE TABLE " + database + "." + tableName;
        try (Connection conn = DriverManager.getConnection(url, username, jwtToken);
             ResultSet rs = conn.createStatement().executeQuery(query)) {
            while (rs.next()) {
                columns.add(rs.getString("name"));  // or rs.getString(1)
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching columns for table "
                    + tableName + ": " + e.getMessage(), e);
        }
        return columns;
    }
}


