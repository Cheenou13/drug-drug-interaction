package lucene.engine.lucene.controller;

// import lucene.engine.lucene.service.LuceneService;
// import lucene.engine.lucene.service.OpenAIService;
// import lucene.engine.lucene.service.OpenFDAService;
// import lucene.engine.lucene.service.OpenFDAResponse;
// import org.apache.lucene.queryparser.classic.ParseException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

import lucene.engine.lucene.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ApiController {

    // private final OpenFDAService openFDAService;
    // private final LuceneService luceneService;
    private final OpenAIService openAIService;

    @GetMapping("/")
    public String greet() {
        // Call to create the database when the root URL is hit
        try {
            // DatabaseManager.createTestDatabase();
            return "Database created and Hello from Backend Service";
        } catch (Exception e) {
            return "Error creating database: " + e.getMessage();
        }
    }

    @Autowired
    public ApiController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/api/drug-interactions")
    public String getDrugInteractions(@RequestParam String drugA, @RequestParam String drugB, @RequestParam String interactions) {
        return openAIService.summarizeInteractions(drugA, drugB, interactions);
    }

    // Add more endpoints as needed
}


