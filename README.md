# ClickHouse Ingestion Tool

## Overview
A simple Spring Boot web app + HTML/JS UI for bidirectional data ingestion between ClickHouse and CSV files.  
Supports column selection and row‑count reporting.

## Features
- List ClickHouse tables  
- List columns for a selected table  
- Export selected columns to CSV  
- Import data from CSV into ClickHouse  

## Technologies Used
- Java 17  
- Spring Boot  
- ClickHouse JDBC  
- OpenCSV  
- Maven  

## Setup

### Prerequisites
- Java 17+  
- Maven 3.6+  
- Docker (to run ClickHouse)

### Run Locally
1. **Clone Repo**  
   ```bash
   git clone https://github.com/Webdevja/clickhouse-ingestion-tool.git
   cd clickhouse-ingestion-tool

2.Start ClickHouse

bash
Copy
Edit
docker run -d --name clickhouse-server -p 8123:8123 clickhouse/clickhouse-server

3.Build & Run

bash
Copy
Edit
mvn clean spring-boot:run

4.Open UI
Visit http://localhost:8080 in your browser.

API Endpoints-

a. GET /api/test
Health check (no auth needed).

b. POST /api/list-tables
List tables in ClickHouse.
json
Copy

{
  "host":"localhost",
  "port":"8123",
  "database":"default",
  "username":"default",
  "jwtToken":"yourToken"
}
c. POST /api/export
Export to CSV.
json
Copy

{
  "host":"localhost",
  "port":"8123",
  "database":"default",
  "username":"default",
  "jwtToken":"yourToken",
  "tableName":"my_table",
  "selectedColumns":["col1","col2"],
  "outputFilePath":"C:/temp/out.csv"
}
d. POST /api/import
Import from CSV.
json
Copy

{
  "host":"localhost",
  "port":"8123",
  "database":"default",
  "username":"default",
  "jwtToken":"yourToken",
  "tableName":"my_table",
  "selectedColumns":["col1","col2"],
  "csvFilePath":"C:/temp/in.csv"
}

License
This project is licensed under the MIT License. See the LICENSE file for details.

sql
Copy


1. Create a file `README.md` in your project root.  
2. Paste the content above (replace `yourToken` with whatever dummy token you use).  
3. Commit and push:

```bash
git add README.md
git commit -m "Add final README"
git push origin main
