package backend.services.service;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
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
        ChatMessage systemMessage = new ChatMessage("system", "You are an expert in summarizing drug interactions.");
        ChatMessage userMessage = new ChatMessage("user", "Summarize the following drug interactions: " + interactions);
        
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(model)
                .messages(Arrays.asList(systemMessage, userMessage))
                .maxTokens(300)
                .build();
        
        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(request).getChoices();
        return choices.get(0).getMessage().getContent().trim();
    }

    public String determineSeverity(String interactionSummary) {
        ChatMessage systemMessage = new ChatMessage("system", "You are an expert in drug interactions.");
        ChatMessage userMessage = new ChatMessage("user", "Determine the severity of the following drug interaction summary: " + interactionSummary + " Severity can be major, moderate, minor, or none.");
        
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(model)
                .messages(Arrays.asList(systemMessage, userMessage))
                .maxTokens(20)
                .build();
        
        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(request).getChoices();
        String severityLevel = choices.get(0).getMessage().getContent().trim().toLowerCase();
        
        if (severityLevel.contains("major")) return "major";
        if (severityLevel.contains("moderate")) return "moderate";
        if (severityLevel.contains("minor")) return "minor";
        return "none";
    }

    public void saveInteraction(String drugA, String drugB, boolean isDdi, String severity, String interactionDescription) throws IOException {
        File file = new File("DDI-cache/ddi_labels.xlsx");
        Workbook workbook;
        Sheet sheet;

        if (!file.exists()) {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("DDI Labels");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("drug_A");
            header.createCell(1).setCellValue("drug_B");
            header.createCell(2).setCellValue("is_ddi");
            header.createCell(3).setCellValue("severity");
            header.createCell(4).setCellValue("interaction_description");
        } else {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            fis.close();
        }

        int rowNum = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(drugA);
        row.createCell(1).setCellValue(drugB);
        row.createCell(2).setCellValue(isDdi);
        row.createCell(3).setCellValue(severity);
        row.createCell(4).setCellValue(interactionDescription);

        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }
}