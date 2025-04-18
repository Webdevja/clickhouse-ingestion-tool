package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.opencsv.CSVWriter;

public class ClickHouseExporter {
    public static void exportToCSV(ClickHouseService service, String tableName, String filePath) {
    }
    private final Connection connection;

    public ClickHouseExporter(Connection connection) {
            this.connection = connection;
        }

        public void exportTableToCSV (String tableName, String outputFilePath){
            String query = "SELECT * FROM " + tableName;
            try (
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))
            ) {
                int columnCount = rs.getMetaData().getColumnCount();
                String[] headers = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    headers[i - 1] = rs.getMetaData().getColumnName(i);
                }
                writer.writeNext(headers);

                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getString(i);
                    }
                    writer.writeNext(row);
                }

                System.out.println("Data exported to " + outputFilePath);
            } catch (Exception e) {
                System.err.println("Export failed: " + e.getMessage());
            }
        }
    }
