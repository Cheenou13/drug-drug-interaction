package lucene.engine.lucene.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneService {
    private static final String INDEX_DIR = "indexDir";

    public void indexDocuments(OpenFDAResponse response) throws IOException {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);

        Document doc = new Document();
        doc.add(new TextField("drug_interactions", response.getDrugInteractions(), Field.Store.YES));
        doc.add(new TextField("adverse_reactions", response.getAdverseReactions(), Field.Store.YES));

        writer.addDocument(doc);
        writer.close();
    }

    public List<String> searchDocuments(String queryStr) throws IOException, ParseException {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        DirectoryReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();

        QueryParser parser = new QueryParser("drug_interactions", analyzer);
        Query query = parser.parse(queryStr);

        TopDocs results = searcher.search(query, 10);
        List<String> summaries = new ArrayList<>();
        for (ScoreDoc scoreDoc : results.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            summaries.add(doc.get("drug_interactions"));
        }
        reader.close();
        return summaries;
    }
}