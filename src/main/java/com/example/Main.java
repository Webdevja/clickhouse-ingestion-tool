package com.example;
import com.example.ClickHouseService;
import com.example.ClickHouseImporter;
import com.example.ClickHouseExporter;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("ClickHouse Ingestion Tool Starting...");


            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        ClickHouseService service = new ClickHouseService(
                "localhost",   // or your Docker host
                "8123",        // HTTPS port
                "default",     // database name
                "default",     // username
                "admin"  // replace with real token
        );

        service.listTables();
        String importFilePath = "data.csv";  // Make sure this file exists!
        String tableName = "test_table";     // The table to import into

        // Perform import
        ClickHouseImporter.importFromCSV(service, importFilePath, tableName);

        // Path to export CSV file
        String exportFilePath = "exported_data.csv";

        // Perform export
        ClickHouseExporter.exportToCSV(service, tableName, exportFilePath);

        System.out.println("Ingestion completed.");
        }
    }
