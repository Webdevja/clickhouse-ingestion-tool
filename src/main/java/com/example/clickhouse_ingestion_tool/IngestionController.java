package com.example.clickhouse_ingestion_tool;

import com.example.ClickHouseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IngestionController {

    @GetMapping("/test")
    public String test() {
        return "Backend is running!";
    }

    // List tables
    @PostMapping("/list-tables")
    public List<String> listTables(@RequestBody ClickHouseConfig cfg) {
        ClickHouseService svc = new ClickHouseService(
                cfg.getHost(), cfg.getPort(), cfg.getDatabase(),
                cfg.getUsername(), cfg.getJwtToken()
        );
        svc.listTables(); // Just prints in console
        return null;
    }

    // Export selected columns to CSV
    @PostMapping("/export")
    public String exportToCsv(@RequestBody ExportRequest req) {
        ClickHouseService svc = new ClickHouseService(
                req.getHost(), req.getPort(), req.getDatabase(),
                req.getUsername(), req.getJwtToken()
        );
        svc.exportToCsv(req.getTableName(), req.getSelectedColumns(), req.getOutputFilePath());
        return "Export initiated";
    }

    // Import CSV to ClickHouse
    @PostMapping("/import")
    public String importFromCsv(@RequestBody ImportRequest req) {
        ClickHouseService svc = new ClickHouseService(
                req.getHost(), req.getPort(), req.getDatabase(),
                req.getUsername(), req.getJwtToken()
        );
        svc.importFromCsv(req.getTableName(), req.getSelectedColumns(), req.getCsvFilePath());
        return "Import initiated";
    }

    // ---------------- DTO CLASSES ----------------

    public static class ClickHouseConfig {
        private String host, port, database, username, jwtToken;

        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }

        public String getPort() { return port; }
        public void setPort(String port) { this.port = port; }

        public String getDatabase() { return database; }
        public void setDatabase(String database) { this.database = database; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getJwtToken() { return jwtToken; }
        public void setJwtToken(String jwtToken) { this.jwtToken = jwtToken; }
    }

    public static class ExportRequest extends ClickHouseConfig {
        private String tableName;
        private List<String> selectedColumns;
        private String outputFilePath;

        public String getTableName() { return tableName; }
        public void setTableName(String tableName) { this.tableName = tableName; }

        public List<String> getSelectedColumns() { return selectedColumns; }
        public void setSelectedColumns(List<String> selectedColumns) { this.selectedColumns = selectedColumns; }

        public String getOutputFilePath() { return outputFilePath; }
        public void setOutputFilePath(String outputFilePath) { this.outputFilePath = outputFilePath; }
    }

    public static class ImportRequest extends ClickHouseConfig {
        private String tableName;
        private List<String> selectedColumns;
        private String csvFilePath;

        public String getTableName() { return tableName; }
        public void setTableName(String tableName) { this.tableName = tableName; }

        public List<String> getSelectedColumns() { return selectedColumns; }
        public void setSelectedColumns(List<String> selectedColumns) { this.selectedColumns = selectedColumns; }

        public String getCsvFilePath() { return csvFilePath; }
        public void setCsvFilePath(String csvFilePath) { this.csvFilePath = csvFilePath; }
    }
}
