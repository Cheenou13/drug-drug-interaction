package lucene.engine.lucene.service;

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

        JsonNode interactionsNode = rootNode.path("drug_interactions");
        JsonNode reactionsNode = rootNode.path("adverse_reactions");

        response.setDrugInteractions(interactionsNode.asText(""));
        response.setAdverseReactions(reactionsNode.asText(""));

        return response;
    }
}
