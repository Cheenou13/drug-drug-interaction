package backend.services;

import backend.services.controller.DirectoryManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendServicesApplication {

    public static void main(String[] args) {
        DirectoryManager.ensureDirectoryExists("DDI-cache");
        SpringApplication.run(BackendServicesApplication.class, args);
    }
}
