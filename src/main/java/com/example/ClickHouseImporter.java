package com.example;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.opencsv.CSVReader;

public class ClickHouseImporter {
    public static void importFromCSV(ClickHouseService service, String filePath, String tableName){}
    private final Connection connection;

    public ClickHouseImporter(Connection connection) {
        this.connection = connection;
    }

    public void importCSVToTable(String csvFilePath, String tableName) {
        try (
                CSVReader reader = new CSVReader(new FileReader(csvFilePath))
        ) {
            String[] headers = reader.readNext(); // read column names
            if (headers == null) {
                throw new Exception("CSV is empty");
            }

            String placeholders = String.join(", ", java.util.Collections.nCopies(headers.length, "?"));
            String sql = "INSERT INTO " + tableName + " VALUES (" + placeholders + ")";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            String[] row;
            while ((row = reader.readNext()) != null) {
                for (int i = 0; i < row.length; i++) {
                    pstmt.setString(i + 1, row[i]);
                }
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Data imported from " + csvFilePath);
        } catch (Exception e) {
            System.err.println("Import failed: " + e.getMessage());
        }
    }
}

