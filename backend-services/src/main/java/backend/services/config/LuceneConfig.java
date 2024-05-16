package backend.services.config;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.NativeFSLockFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class LuceneConfig {

    private static final String INDEX_DIR = "lucene-indexer"; // change this to your desired directory

    @Bean
    public Directory directory() throws IOException {
        return MMapDirectory.open(Paths.get(INDEX_DIR), NativeFSLockFactory.INSTANCE);
    }

    @Bean
    public IndexWriter indexWriter() throws IOException {
        Directory directory = directory();
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        try {
            return new IndexWriter(directory, config);
        } catch (LockObtainFailedException e) {
            // Attempt to release the lock if it's held by the current VM
            if (e.getMessage().contains("Lock held by this virtual machine")) {
                directory.obtainLock(IndexWriter.WRITE_LOCK_NAME).close();
                return new IndexWriter(directory, config);
            } else {
                throw e;
            }
        }
    }

    @Bean
    public IndexSearcher indexSearcher() throws IOException {
        Directory directory = directory();
        if (DirectoryReader.indexExists(directory)) {
            return new IndexSearcher(DirectoryReader.open(directory));
        } else {
            // Create the index if it does not exist
            try (IndexWriter writer = indexWriter()) {
                // You can add some initial documents here if needed
            }
            return new IndexSearcher(DirectoryReader.open(directory));
        }
    }
}
