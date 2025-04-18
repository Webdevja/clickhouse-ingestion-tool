document.getElementById("sourceSelect").addEventListener("change", function () {
    const selected = this.value;
    document.getElementById("clickhouseConfig").style.display = selected === "clickhouse" ? "block" : "none";
    document.getElementById("flatfileConfig").style.display = selected === "flatfile" ? "block" : "none";
});

function connectClickHouse() {
    const host = document.getElementById("host").value;
    const port = document.getElementById("port").value;
    const db = document.getElementById("database").value;
    const user = document.getElementById("user").value;
    const token = document.getElementById("token").value;

    document.getElementById("status").innerText = `Connecting to ClickHouse at ${host}:${port}...`;
    // TODO: Send these details to backend via AJAX
}

function loadColumns() {
    // TODO: AJAX to backend -> fetch table/column info
    document.getElementById("columnsContainer").innerHTML = `
        <label><input type="checkbox" checked> id </label>
        <label><input type="checkbox" checked> name </label>
    `;
}

function startIngestion() {
    // TODO: trigger ingestion logic on backend via AJAX
    document.getElementById("status").innerText = "Ingestion started...";
}
