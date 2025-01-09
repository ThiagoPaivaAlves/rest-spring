package com.thiago.services;

import com.thiago.config.FileConfiguration;
import com.thiago.exceptions.CustomFileNotFoundExceptionException;
import com.thiago.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {
    
    private final Path fileStoragePathLocation;
    
    @Autowired
    public FileStorageService(FileConfiguration config) {
        this.fileStoragePathLocation = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();
        
        try {
            Files.createDirectories(this.fileStoragePathLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create directory where uploaded files would be stored", e);
        }
    }
    
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try{
            if(fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid char sequence" + fileName);
            }
            
            Path targetLocation = this.fileStoragePathLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Could not store file "+ fileName + ".Please try again!", e);
        }
        
    }
    
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStoragePathLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new CustomFileNotFoundExceptionException("File not found");
            }
        } catch (Exception e) {
            throw new CustomFileNotFoundExceptionException("File not found" + fileName, e);
        }
    }
}
