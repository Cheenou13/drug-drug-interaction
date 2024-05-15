package lucene.engine.lucene.controller;

import lucene.engine.lucene.service.LuceneService;
import lucene.engine.lucene.service.OpenFDAService;
import lucene.engine.lucene.service.OpenAIService;
import lucene.engine.lucene.service.OpenFDAResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    private final OpenFDAService openFDAService;
    private final LuceneService luceneService;
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
    public ApiController(LuceneService luceneService, OpenFDAService openFDAService, OpenAIService openAIService) {
        this.luceneService = luceneService;
        this.openFDAService = openFDAService;
        this.openAIService = openAIService;
    }

    @GetMapping("/api/drug-interactions")
    public Map<String, Object> getDrugInteractions(@RequestParam String drugA, @RequestParam String drugB) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Check the cache first
            String cachedInteraction = luceneService.checkCache(drugA, drugB);
            if (cachedInteraction != null) {
                response.put("drug_A", drugA);
                response.put("drug_B", drugB);
                response.put("has_ddi", true);
                response.put("interaction", cachedInteraction);
                return response;
            }

            // If not found in cache, call the OpenFDA API
            String openFdaResponse = openFDAService.getDrugInteractions(drugA, drugB);
            OpenFDAResponse fdaResponse = OpenFDAResponse.fromJson(openFdaResponse);

            String interactions = fdaResponse.getDrugInteractions();
            String adverseReactions = fdaResponse.getAdverseReactions();
            boolean hasDdi = false;
            String interactionSummary = "No interaction found at this moment.";
            String severity = "none";

            if (interactions.contains(drugA) && interactions.contains(drugB)) {
                hasDdi = true;
                interactionSummary = openAIService.summarizeInteractions(interactions);
                severity = openAIService.determineSeverity(interactionSummary);
            } else if (adverseReactions.contains(drugA) && adverseReactions.contains(drugB)) {
                hasDdi = true;
                interactionSummary = openAIService.summarizeInteractions(adverseReactions);
                severity = openAIService.determineSeverity(interactionSummary);
            } else if ((interactions.contains(drugA) && adverseReactions.contains(drugB)) ||
                       (interactions.contains(drugB) && adverseReactions.contains(drugA))) {
                hasDdi = true;
                interactionSummary = openAIService.summarizeInteractions(interactions + " " + adverseReactions);
                severity = openAIService.determineSeverity(interactionSummary);
            }

            if (!hasDdi) {
                interactionSummary = String.format("Currently no drug-drug interaction between %s and %s. %s is used for [description]. %s is used for [description].", drugA, drugB, drugA, drugB);
            }

            // Save the result in the cache
            openAIService.saveInteraction(drugA, drugB, hasDdi, severity, interactionSummary);

            // Return the response
            response.put("drug_A", drugA);
            response.put("drug_B", drugB);
            response.put("has_ddi", hasDdi);
            response.put("interaction", interactionSummary);

        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    // Add more endpoints as needed
}


