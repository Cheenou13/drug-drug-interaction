package lucene.engine.lucene.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Autowired
    private OpenAIConfig openAIConfig;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String summarizeText(String text) throws IOException {
        String apiKey = openAIConfig.getApiKey();
        String apiEndpoint = openAIConfig.getApiEndpoint();

        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", text);
        payload.put("max_tokens", 100);

        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(payload));

        HttpPost post = new HttpPost(apiEndpoint);
        post.setEntity(entity);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Authorization", "Bearer " + apiKey);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {

            Map<String, Object> responseMap = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<Map<String, Object>>() {});
            Object choicesObj = responseMap.get("choices");

            if (choicesObj instanceof List) {
                List<?> choicesList = (List<?>) choicesObj;
                if (!choicesList.isEmpty() && choicesList.get(0) instanceof Map) {
                    Map<?, ?> firstChoice = (Map<?, ?>) choicesList.get(0);
                    return firstChoice.get("text").toString();
                }
            }
            throw new IOException("Unexpected response structure");
        }
    }
}