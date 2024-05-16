// package lucene.engine.lucene.service;

// import org.apache.lucene.analysis.custom.CustomAnalyzer;
// import org.apache.lucene.queryparser.classic.QueryParser;
// import org.apache.lucene.search.IndexSearcher;
// import org.apache.lucene.search.Query;
// import org.apache.lucene.search.ScoreDoc;
// import org.apache.lucene.search.TopDocs;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.io.IOException;
// import java.nio.charset.StandardCharsets;
// import java.nio.file.*;
// import java.util.List;

// @Service
// public class LuceneService {
//     private static final String INDEX_DIR = "DDI-cache/ddi_labels.data";
//     private final IndexSearcher searcher;

//     @Autowired
//     public LuceneService(IndexSearcher searcher) {
//         this.searcher = searcher;
//     }

//     public String[] checkCache(String drugA, String drugB) throws IOException {
//         Path filePath = Paths.get(INDEX_DIR);
//         if (!Files.exists(filePath)) {
//             return null; // File does not exist, no cache to check
//         }
//         List<String> lines = Files.readAllLines(Paths.get("DDI-cache/ddi_labels.data"));
//         for (String line : lines) {
//             String[] parts = line.split(",");
//             if ((parts[0].equalsIgnoreCase(drugA) && parts[1].equalsIgnoreCase(drugB)) ||
//                 (parts[0].equalsIgnoreCase(drugB) && parts[1].equalsIgnoreCase(drugA))) {
//                 return new String[]{parts[4], parts[3]}; // return the cached interaction description and severity
//             }
//         }
//         return null;
//     }

//     public String searchDrugInteractions(String drugA, String drugB) throws Exception {
//         CustomAnalyzer analyzer = CustomAnalyzer.builder()
//                 .withTokenizer("standard")
//                 .addTokenFilter("lowercase")
//                 .addTokenFilter("stop")
//                 .build();
//         QueryParser parser = new QueryParser("interaction_description", analyzer);
//         Query query = parser.parse("\"" + drugA + "\" AND \"" + drugB + "\"");
//         TopDocs results = searcher.search(query, 10);
//         return extractContent(results);
//     }

//     public String searchAdverseReactions(String drugA, String drugB) throws Exception {
//         CustomAnalyzer analyzer = CustomAnalyzer.builder()
//                 .withTokenizer("standard")
//                 .addTokenFilter("lowercase")
//                 .addTokenFilter("stop")
//                 .build();
//         QueryParser parser = new QueryParser("interaction_description", analyzer);
//         Query query = parser.parse("\"" + drugA + "\" AND \"" + drugB + "\"");
//         TopDocs results = searcher.search(query, 10);
//         return extractContent(results);
//     }

//     private String extractContent(TopDocs results) throws IOException {
//         StringBuilder content = new StringBuilder();
//         for (ScoreDoc scoreDoc : results.scoreDocs) {
//             org.apache.lucene.document.Document doc = searcher.doc(scoreDoc.doc);
//             content.append(doc.get("interaction_description")).append("\n");
//         }
//         return content.toString();
//     }

//     public void saveToCache(String drugA, String drugB, boolean isDDI, String severity, String interactionDescription) throws IOException {
//         Path filePath = Paths.get(INDEX_DIR);
//         if (!Files.exists(filePath)) {
//             Files.createDirectories(filePath.getParent());
//             Files.createFile(filePath);
//         }
//         String record = drugA + "," + drugB + "," + isDDI + "," + severity + "," + interactionDescription + "\n";
//         Files.write(filePath, record.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
//     }
// }

package lucene.engine.lucene.service;

import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

@Service
public class LuceneService {
    private final IndexSearcher searcher;

    @Autowired
    public LuceneService(IndexSearcher searcher) {
        this.searcher = searcher;
    }

    public String[] checkCache(String drugA, String drugB) throws IOException {
        File file = new File("DDI-cache/ddi_labels.xlsx");
        if (!file.exists()) {
            return null; // File does not exist, no cache to check
        }
        
        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
    
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String cellDrugA = row.getCell(0).getStringCellValue();
                String cellDrugB = row.getCell(1).getStringCellValue();
                if ((cellDrugA.equalsIgnoreCase(drugA) && cellDrugB.equalsIgnoreCase(drugB)) ||
                    (cellDrugA.equalsIgnoreCase(drugB) && cellDrugB.equalsIgnoreCase(drugA))) {
                    String interactionDescription = row.getCell(4).getStringCellValue();
                    String severity = row.getCell(3).getStringCellValue();
                    return new String[]{interactionDescription, severity};
                }
            }
        }
        return null;
    }

    public String searchDrugInteractions(String drugA, String drugB) throws Exception {
        CustomAnalyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer("standard")
                .addTokenFilter("lowercase")
                .addTokenFilter("stop")
                .build();
        QueryParser parser = new QueryParser("interaction_description", analyzer);
        Query query = parser.parse("\"" + drugA + "\" AND \"" + drugB + "\"");
        TopDocs results = searcher.search(query, 10);
        return extractContent(results);
    }

    public String searchAdverseReactions(String drugA, String drugB) throws Exception {
        CustomAnalyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer("standard")
                .addTokenFilter("lowercase")
                .addTokenFilter("stop")
                .build();
        QueryParser parser = new QueryParser("interaction_description", analyzer);
        Query query = parser.parse("\"" + drugA + "\" AND \"" + drugB + "\"");
        TopDocs results = searcher.search(query, 10);
        return extractContent(results);
    }

    private String extractContent(TopDocs results) throws IOException {
        StringBuilder content = new StringBuilder();
        for (ScoreDoc scoreDoc : results.scoreDocs) {
            org.apache.lucene.document.Document doc = searcher.doc(scoreDoc.doc);
            content.append(doc.get("interaction_description")).append("\n");
        }
        return content.toString();
    }

    public void saveToCache(String drugA, String drugB, boolean isDDI, String severity, String interactionDescription) throws IOException {
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
        row.createCell(2).setCellValue(isDDI);
        row.createCell(3).setCellValue(severity);
        row.createCell(4).setCellValue(interactionDescription);

        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }
}
