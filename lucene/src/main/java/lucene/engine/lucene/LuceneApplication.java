package lucene.engine.lucene;

import lucene.engine.lucene.controller.DirectoryManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LuceneApplication {

    public static void main(String[] args) {
        DirectoryManager.ensureDirectoryExists("DDI-cache");
        SpringApplication.run(LuceneApplication.class, args);
    }
}
