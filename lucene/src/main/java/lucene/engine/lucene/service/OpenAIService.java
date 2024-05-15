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

import java.io.*;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;

// @Service
// public class OpenAIService {
//     private final OpenAiService openAiService;
//     private final String model;

//     public OpenAIService(@Value("${openai.api.key}") String apiKey, @Value("${openai.model}") String model) {
//         this.openAiService = new OpenAiService(apiKey);
//         this.model = model;
//     }

//     public String summarizeInteractions(String interactions) {
//         ChatMessage userMessage = new ChatMessage("user", "Summarize the following drug interactions: " + interactions);

//         ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
//                 .model(model)
//                 .messages(Collections.singletonList(userMessage))
//                 .maxTokens(300)
//                 .build();

//         ChatMessage choice = openAiService.createChatCompletion(chatRequest).getChoices().get(0).getMessage();
//         return choice.getContent();
//     }
// }

@Service
public class OpenAIService {
    private final OpenAiService openAiService;
    private final String model;
    private final String cacheDir = "DDI-cache";
    private final String cacheFile = "ddi_label.data";

    public OpenAIService(@Value("${openai.api.key}") String apiKey, @Value("${openai.model}") String model) {
        this.openAiService = new OpenAiService(apiKey);
        this.model = model;
        createCacheDir();
    }

    private void createCacheDir() {
        Path path = Paths.get(cacheDir);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveToCache(String drugA, String drugB, String summary) {
        Path path = Paths.get(cacheDir, cacheFile);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(drugA + "," + drugB + "," + summary);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String checkCache(String drugA, String drugB) {
        Path path = Paths.get(cacheDir, cacheFile);
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if ((parts[0].equals(drugA) && parts[1].equals(drugB)) || (parts[0].equals(drugB) && parts[1].equals(drugA))) {
                        return parts[2];
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String summarizeInteractions(String drugA, String drugB, String interactions) {
        String cacheResult = checkCache(drugA, drugB);
        if (cacheResult != null) {
            return cacheResult;
        }

        ChatMessage userMessage = new ChatMessage("user", "Summarize the following drug interactions between " + drugA + " and " + drugB + ": " + interactions);
        ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                .model(model)
                .messages(Collections.singletonList(userMessage))
                .maxTokens(300)
                .build();
        // CompletionRequest completionRequest = CompletionRequest.builder()
        //         .model(model)
        //         .prompt("Summarize the following drug interactions between " + drugA + " and " + drugB + ": " + interactions)
        //         .maxTokens(150)
        //         .build();

        // ChatCompletionChoice choice = openAiService.createChatCompletion(chatRequest).getChoices().get(0).getMessage();
        ChatCompletionChoice choice = openAiService.createChatCompletion(chatRequest).getChoices().get(0);
        // CompletionChoice  choice = openAiService.createCompletion(chatRequest).getChoices().get(0);
        // String summary = choice.getContent();
        String summary = choice.getMessage().getContent();

        saveToCache(drugA, drugB, summary);
        return summary;
    }
}