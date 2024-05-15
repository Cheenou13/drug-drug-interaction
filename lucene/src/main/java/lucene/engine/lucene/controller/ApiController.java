package lucene.engine.lucene.controller;

import lucene.engine.lucene.service.PubMedResponse;
import lucene.engine.lucene.service.PubMedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final PubMedService pubMedService;

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
    public ApiController(PubMedService pubMedService) {
        this.pubMedService = pubMedService;
    }

    @GetMapping("/searchDDI")
    public PubMedResponse searchDDI(@RequestParam String drugA, @RequestParam String drugB) {
        return pubMedService.searchDDI(drugA, drugB);
    }

    // Add more endpoints as needed
}


