package backend.services.controller;
import java.io.File;

public class DirectoryManager {
    public static void ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
