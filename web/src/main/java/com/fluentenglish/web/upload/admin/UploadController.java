package com.fluentenglish.web.upload.admin;

import com.fluentenglish.web.upload.cloud.StorageService;
import com.fluentenglish.web.upload.cloud.UploadedFileDto;
import com.fluentenglish.web.upload.cloud.exception.UploadFileNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/admin/upload")
public class UploadController {
    private final StorageService storageService;

    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<Map<?,?>> upload(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("folder") String folder) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        UploadedFileDto uploadedFile = storageService.uploadFile(file.getInputStream(), folder);

        return ResponseEntity.ok(Map.of("data", uploadedFile));
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<Map<?,?>> getFileInfo(@PathVariable String fileId) {
        try {
            UploadedFileDto uploadedFile = storageService.getFileData(fileId);
            return ResponseEntity.ok(Map.of("data", uploadedFile));
        } catch (UploadFileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileId) {
        storageService.deleteFile(fileId);

        return ResponseEntity.ok().build();
    }
}
