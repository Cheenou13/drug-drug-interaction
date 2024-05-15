package lucene.engine.lucene.service;

// import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryparser.classic.QueryParser;
// import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LuceneService {

    private final IndexSearcher searcher;

    @Autowired
    public LuceneService(IndexSearcher searcher) {
        this.searcher = searcher;
    }

    public String checkCache(String drugA, String drugB) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("DDI-cache/ddi_labels.data"));
        for (String line : lines) {
            String[] parts = line.split(",");
            if ((parts[0].equalsIgnoreCase(drugA) && parts[1].equalsIgnoreCase(drugB)) ||
                (parts[0].equalsIgnoreCase(drugB) && parts[1].equalsIgnoreCase(drugA))) {
                return parts[4]; // return the cached interaction description
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
        Path filePath = Paths.get("DDI-cache/ddi_labels.data");
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }
        String record = drugA + "," + drugB + "," + isDDI + "," + severity + "," + interactionDescription + "\n";
        Files.write(filePath, record.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
    }
}
