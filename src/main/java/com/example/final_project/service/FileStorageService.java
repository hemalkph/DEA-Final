package com.example.final_project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    /**
     * Allowed image content types
     */
    private static final java.util.Set<String> ALLOWED_CONTENT_TYPES = java.util.Set.of(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/webp");

    /**
     * Store a single file and return its URL path
     */
    public String storeFile(MultipartFile file) {
        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            log.warn("Rejected file upload - invalid content type: {}", contentType);
            throw new RuntimeException(
                    "Invalid file type. Only JPEG, PNG, and WebP images are allowed. Received: " + contentType);
        }

        // Normalize file name and generate unique name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";

        if (originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        try {
            // Check for invalid path characters
            if (uniqueFileName.contains("..")) {
                throw new RuntimeException("Invalid file path: " + uniqueFileName);
            }

            // Copy file to target location
            Path targetLocation = this.uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("File stored successfully: {} -> {} (size: {} bytes)",
                    originalFileName, uniqueFileName, file.getSize());

            // Return URL path that can be used to access the file
            return "/api/files/" + uniqueFileName;
        } catch (IOException e) {
            log.error("Failed to store file {}: {}", uniqueFileName, e.getMessage());
            throw new RuntimeException("Could not store file " + uniqueFileName, e);
        }
    }

    /**
     * Store multiple files and return list of URL paths
     */
    public List<String> storeFiles(MultipartFile[] files) {
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                fileUrls.add(storeFile(file));
            }
        }
        return fileUrls;
    }

    /**
     * Load file as Resource
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.uploadPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        }
    }

    /**
     * Delete a file
     */
    public void deleteFile(String fileName) {
        try {
            Path filePath = this.uploadPath.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + fileName, e);
        }
    }
}
