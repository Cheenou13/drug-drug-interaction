package lucene.engine.lucene.service;


import com.theokanning.openai.service.OpenAiService;
// import com.theokanning.openai
// import com.theokanning.openai.chat.ChatRequest;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.nio.file.StandardOpenOption;
// import java.util.List;

// @Service
// public class OpenAIService {
//     private final OpenAiService openAiService;
//     private final String model;
//     private final String cacheDir = "DDI-cache";
//     private final String cacheFile = "ddi_label.data";

//     public OpenAIService(@Value("${openai.api.key}") String apiKey, @Value("${openai.model}") String model) {
//         this.openAiService = new OpenAiService(apiKey);
//         this.model = model;
//         createCacheDir();
//     }

//     private void createCacheDir() {
//         Path path = Paths.get(cacheDir);
//         if (!Files.exists(path)) {
//             try {
//                 Files.createDirectory(path);
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

//     private void saveToCache(String drugA, String drugB, String summary) {
//         Path path = Paths.get(cacheDir, cacheFile);
//         try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
//             writer.write(drugA + "," + drugB + "," + summary);
//             writer.newLine();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     private String checkCache(String drugA, String drugB) {
//         Path path = Paths.get(cacheDir, cacheFile);
//         if (Files.exists(path)) {
//             try (BufferedReader reader = Files.newBufferedReader(path)) {
//                 String line;
//                 while ((line = reader.readLine()) != null) {
//                     String[] parts = line.split(",");
//                     if ((parts[0].equals(drugA) && parts[1].equals(drugB)) || (parts[0].equals(drugB) && parts[1].equals(drugA))) {
//                         return parts[2];
//                     }
//                 }
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         }
//         return null;
//     }

//     public String summarizeInteractions(String drugA, String drugB, String interactions) {
//         String cacheResult = checkCache(drugA, drugB);
//         if (cacheResult != null) {
//             return cacheResult;
//         }

//         ChatMessage userMessage = new ChatMessage("user", "Summarize the following drug interactions between " + drugA + " and " + drugB + ": " + interactions);
//         ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
//                 .model(model)
//                 .messages(Collections.singletonList(userMessage))
//                 .maxTokens(300)
//                 .build();
//         // CompletionRequest completionRequest = CompletionRequest.builder()
//         //         .model(model)
//         //         .prompt("Summarize the following drug interactions between " + drugA + " and " + drugB + ": " + interactions)
//         //         .maxTokens(150)
//         //         .build();

//         // ChatCompletionChoice choice = openAiService.createChatCompletion(chatRequest).getChoices().get(0).getMessage();
//         ChatCompletionChoice choice = openAiService.createChatCompletion(chatRequest).getChoices().get(0);
//         // CompletionChoice  choice = openAiService.createCompletion(chatRequest).getChoices().get(0);
//         // String summary = choice.getContent();
//         String summary = choice.getMessage().getContent();

//         saveToCache(drugA, drugB, summary);
//         return summary;
//     }
// }

// @Service
// public class OpenAIService {
//     private final OpenAiService openAiService;
//     private final String model;
//     private final Map<String, String> cache = new ConcurrentHashMap<>();

//     public OpenAIService(@Value("${openai.api.key}") String apiKey, @Value("${openai.model}") String model) {
//         this.openAiService = new OpenAiService(apiKey);
//         this.model = model;
//     }

//     public String summarizeInteractions(String drugA, String drugB, String interactions) {
//         String cacheKey = drugA + "_" + drugB;
//         if (cache.containsKey(cacheKey)) {
//             return cache.get(cacheKey);
//         }

//         String prompt = interactions.isEmpty()
//                 ? String.format("There is currently no known drug-drug interaction between %s and %s. Provide a short paragraph about what each drug does.", drugA, drugB)
//                 : String.format("Summarize the following drug interactions between %s and %s: %s", drugA, drugB, interactions);

//         List<ChatMessage> messages = new ArrayList<>();
//         messages.add(new ChatMessage("system", "You are a helpful assistant."));
//         messages.add(new ChatMessage("user", prompt));

//         ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
//                 .model(model)
//                 .messages(messages)
//                 .maxTokens(150)
//                 .build();

//         // ChatCompletionResponse response = openAiService.createChatCompletion(completionRequest);
//         ChatCompletionChoice choice = openAiService.createChatCompletion(completionRequest).getChoices().get(0);
//         String summary = choice.getMessage().getContent();

//         if (!interactions.isEmpty()) {
//             saveToCache(cacheKey, summary);
//         }

//         return summary;
//     }

//     private void saveToCache(String cacheKey, String content) {
//         cache.put(cacheKey, content);
//         File cacheDir = new File("DDI-cache");
//         if (!cacheDir.exists()) {
//             cacheDir.mkdirs();
//         }
//         File cacheFile = new File(cacheDir, "ddi_label.data");
//         try (FileWriter writer = new FileWriter(cacheFile, true)) {
//             writer.write(String.format("%s: %s%n", cacheKey, content));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }

// import com.theokanning.openai.completion.CompletionRequest;
// import com.theokanning.openai.completion.CompletionChoice;
// import com.theokanning.openai.service.OpenAiService;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

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