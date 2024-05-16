package backend.services.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class OpenFDAResponse {
    private String drugInteractions;
    private String adverseReactions;

    public String getDrugInteractions() {
        return drugInteractions;
    }

    public void setDrugInteractions(String drugInteractions) {
        this.drugInteractions = drugInteractions;
    }

    public String getAdverseReactions() {
        return adverseReactions;
    }

    public void setAdverseReactions(String adverseReactions) {
        this.adverseReactions = adverseReactions;
    }

    public static OpenFDAResponse fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        OpenFDAResponse response = new OpenFDAResponse();

        JsonNode resultsNode = rootNode.path("results");
        if (resultsNode.isArray() && resultsNode.size() > 0) {
            JsonNode firstResult = resultsNode.get(0);
            JsonNode interactionsNode = firstResult.path("drug_interactions");
            JsonNode reactionsNode = firstResult.path("adverse_reactions");

            if (interactionsNode.isArray() && interactionsNode.size() > 0) {
                response.setDrugInteractions(interactionsNode.toString());
            } else {
                response.setDrugInteractions("");
            }

            if (reactionsNode.isArray() && reactionsNode.size() > 0) {
                response.setAdverseReactions(reactionsNode.toString());
            } else {
                response.setAdverseReactions("");
            }
        } else {
            response.setDrugInteractions("");
            response.setAdverseReactions("");
        }

        return response;
    }
}
