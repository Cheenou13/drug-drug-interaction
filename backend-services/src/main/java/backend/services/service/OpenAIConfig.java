package backend.services.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiEndpoint() {
        return "https://api.openai.com/v1/engines/davinci-codex/completions";
    }
}
