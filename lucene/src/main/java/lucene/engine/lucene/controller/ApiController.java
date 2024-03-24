package lucene.engine.lucene.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ApiController {

    @GetMapping("/")
    public String greet() {
        return "Hello from Backend Service";
    }

    // Add more endpoints as needed
}
