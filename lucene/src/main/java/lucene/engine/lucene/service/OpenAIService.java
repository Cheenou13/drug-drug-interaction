package lucene.engine.lucene.service;


import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
// import com.theokanning.openai.completion.chat.ChatCompletionRequest;
// import com.theokanning.openai.completion.chat.ChatMessage;
// import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class OpenAIService {
    private final OpenAiService openAiService;
    private final String model;

    public OpenAIService(@Value("${openai.api.key}") String apiKey, @Value("${openai.model}") String model) {
        this.openAiService = new OpenAiService(apiKey);
        this.model = model;
    }

    public String summarizeInteractions(String interactions) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model(model)
                .prompt("Summarize the following drug interactions and suggest the severity (major, moderate, minor): " + interactions)
                .maxTokens(300)
                .build();
        CompletionChoice choice = openAiService.createCompletion(completionRequest).getChoices().get(0);
        return choice.getText().trim();
    }

    public String determineSeverity(String interactionSummary) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model(model)
                .prompt("Determine the severity of the following drug interaction summary: " + interactionSummary + " Severity can be major, moderate, or minor.")
                .maxTokens(20)
                .build();
        List<CompletionChoice> choices = openAiService.createCompletion(completionRequest).getChoices();
        return choices.get(0).getText().trim().toLowerCase();
    }

    public void saveInteraction(String drugA, String drugB, boolean isDdi, String severity, String interactionDescription) throws IOException {
        Path filePath = Paths.get("DDI-cache/ddi_labels.data");
        Files.createDirectories(filePath.getParent());
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        String record = String.join(",", drugA, drugB, String.valueOf(isDdi), severity, interactionDescription);
        Files.write(filePath, (record + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
    }
}