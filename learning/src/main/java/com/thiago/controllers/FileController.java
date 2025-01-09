package com.thiago.controllers;

import com.thiago.models.UploadFileResponse;
import com.thiago.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/file")
@Tag(name = "File", description = "Endpoint for managing file uploading")
@Slf4j
public class FileController {
    
    @Autowired
    private FileStorageService service;
    
    @PostMapping("/upload")
    public UploadFileResponse uploadFile(@RequestParam("file")MultipartFile file) {
        log.info("storing file to disk");
        String fileName = service.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                            .path("/api/file/download/").path(fileName)
                                                            .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }
    
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName")String file, HttpServletRequest request) {
        log.info("reading a file on disk");
        
        Resource resource = service.loadFileAsResource(file);
        String contentType = "";
        
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if(contentType.isBlank()) {
                contentType = "application/octet-stream";
            }
        } catch (Exception e) {
            log.info("Could not determine file type");
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"" + resource.getFilename() + "\"")
                             .body(resource);
    }
}
