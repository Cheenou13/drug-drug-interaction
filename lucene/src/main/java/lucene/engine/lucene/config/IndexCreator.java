// package lucene.engine.lucene.config;

// import org.apache.lucene.index.IndexWriter;
// import org.apache.lucene.index.IndexWriterConfig;
// import org.apache.lucene.store.MMapDirectory;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.io.IOException;
// import java.nio.file.Paths;

// @Configuration
// public class IndexCreator {

//     @Bean
//     public IndexWriter createIndex() throws IOException {
//         // Change the path to a suitable directory on your system
//         MMapDirectory directory = new MMapDirectory(Paths.get("lucene-indexer"));
//         IndexWriterConfig config = new IndexWriterConfig();
//         return new IndexWriter(directory, config);
//     }
// }