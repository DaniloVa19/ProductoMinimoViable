package com.rollerspeed.rollerspeed.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadDirInitializer {

    private static final String UPLOAD_DIR = "C:/Temp/uploads/";

    @PostConstruct
    public void createUploadDir() {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs(); // crea el directorio y subdirectorios si no existen
            System.out.println("Directorio creado: " + UPLOAD_DIR);
        }
    }
}
