package lucene.engine.lucene.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ApiController {

    @GetMapping("/")
    public String greet() {
        // Call to create the database when the root URL is hit
        try {
            DatabaseManager.createTestDatabase();
            return "Database created and Hello from Backend Service";
        } catch (Exception e) {
            return "Error creating database: " + e.getMessage();
        }
    }

    // Add more endpoints as needed
}
