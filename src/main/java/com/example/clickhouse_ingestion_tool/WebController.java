package com.example.clickhouse_ingestion_tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index.html"; // Make sure index.html is in the static folder
    }
}
