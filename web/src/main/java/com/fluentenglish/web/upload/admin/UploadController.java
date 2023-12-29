package com.fluentenglish.web.upload.admin;

import com.fluentenglish.web.common.utils.FilenameUtils;
import com.fluentenglish.web.upload.cloud.StorageService;
import com.fluentenglish.web.upload.cloud.UploadDto;
import com.fluentenglish.web.upload.cloud.UploadedFileDto;
import com.fluentenglish.web.upload.cloud.exception.UploadFileNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/api/upload")
public class UploadController {
    private final StorageService storageService;

    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<UploadedFileDto> upload(@RequestParam("file") MultipartFile file,
                                            @RequestParam("folder") String folder,
                                            @RequestParam(value = "temporary", required = false, defaultValue = "true")
                                            boolean temporary) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        UploadDto uploadDto = new UploadDto();
        uploadDto.setInputStream(file.getInputStream());
        uploadDto.setFolder(folder);
        uploadDto.setMimeType(file.getContentType());
        uploadDto.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()).orElse(null));

        UploadedFileDto uploadedFile = temporary
                ? storageService.uploadFileTemp(uploadDto)
                : storageService.uploadFile(uploadDto);

        return ResponseEntity.ok(uploadedFile);
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<UploadedFileDto> getFileInfo(@PathVariable String fileId) {
        try {
            UploadedFileDto uploadedFile = storageService.getFileData(fileId);
            return ResponseEntity.ok(uploadedFile);
        } catch (UploadFileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/file/{fileId}/permanent")
    public ResponseEntity<?> makeFilePermanent(@PathVariable String fileId) {
        storageService.makeFilePermanent(fileId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileId) {
        storageService.deleteFile(fileId);

        return ResponseEntity.ok().build();
    }
}
