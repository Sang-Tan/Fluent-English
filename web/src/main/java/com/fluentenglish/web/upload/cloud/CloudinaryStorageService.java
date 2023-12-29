package com.fluentenglish.web.upload.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.exceptions.NotFound;
import com.fluentenglish.web.upload.cloud.exception.UploadFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryStorageService implements StorageService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Cloudinary cloudinary;

    private final int deleteBatchSize;

    private final TemporaryFileRepository temporaryFileRepository;

    public CloudinaryStorageService(@Value("${upload.cloudinary.url}") String cloudinaryUrl,
                                    @Value("${upload.cloudinary.delete-batch-size}") int deleteBatchSize,
                                    TemporaryFileRepository temporaryFileRepository) {
        this.cloudinary = new Cloudinary(cloudinaryUrl);
        this.deleteBatchSize = deleteBatchSize;
        this.temporaryFileRepository = temporaryFileRepository;
    }

    @Override
    public UploadedFileDto uploadFile(UploadDto uploadDto) {
        String folder = uploadDto.getFolder();

        Map<String, Object> params = new HashMap<>();
        params.put("folder", folder == null ? "" : folder);
        params.put("resource_type", getResourceType(uploadDto));

        File tempFile = createTempFile(uploadDto.getInputStream(), uploadDto.getExtension());
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(tempFile, params);

            tempFile.delete();
            return getUploadedFileDto(uploadResult);
        } catch (IOException e) {
            tempFile.delete();
            throw new RuntimeException(e);
        }
    }

    @Override
    public UploadedFileDto uploadFileTemp(UploadDto uploadDto) {
        UploadedFileDto uploadedFileDto = uploadFile(uploadDto);

        TemporaryFile temporaryFile = new TemporaryFile();
        temporaryFile.setId(uploadedFileDto.getId());
        temporaryFile.setUploadDate(new Date());

        try {
            temporaryFileRepository.save(temporaryFile);
        } catch (Exception e) {
            logger.error("Failed to save temporary file to database", e);
            deleteFileFromCloud(uploadedFileDto.getId());
            throw new RuntimeException(e);
        }

        return uploadedFileDto;
    }

    @Override
    public void makeFilePermanent(String fileId) {
        temporaryFileRepository.deleteById(fileId);
    }

    @Override
    public UploadedFileDto getFileData(String fileId) {
        try {
            Map<?, ?> result = cloudinary.api().resource(fileId, Map.of());
            return getUploadedFileDto(result);
        } catch (NotFound e) {
            throw new UploadFileNotFoundException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String fileId) {
        deleteFileFromCloud(fileId);
        temporaryFileRepository.deleteById(fileId);
    }

    @Override
    public void deleteFiles(List<String> fileIds) {
        int fromIndex = 0;
        int toIndex = Math.min(deleteBatchSize, fileIds.size());

        while (fromIndex < fileIds.size()) {
            deleteBatch(fileIds.subList(fromIndex, toIndex));

            fromIndex = toIndex;
            toIndex = Math.min(toIndex + deleteBatchSize, fileIds.size());
        }
    }

    private String getResourceType(UploadDto uploadDto) {
        String mimeType = uploadDto.getMimeType();

        if (mimeType.startsWith("image")) {
            return "image";
        } else if (mimeType.startsWith("video")) {
            return "video";
        } else {
            return "auto";
        }
    }

    private void deleteBatch(List<String> fileIdsInBatch) {
        try {
            cloudinary.api().deleteResources(fileIdsInBatch, Map.of());
            temporaryFileRepository.deleteAllById(fileIdsInBatch);
        } catch (Exception e) {
            logger.error("Failed to delete files in batch", e);
        }
    }

    private void deleteFileFromCloud(String fileId) {
        try {
            cloudinary.uploader().destroy(fileId, Map.of());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File createTempFile(InputStream inputStream, String fileExtension) {
        try {
            File tempFile = File.createTempFile("FluentEnglish" + File.separator,
                    fileExtension == null ? "" : "." + fileExtension);
            try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
                inputStream.transferTo(outputStream);
            }

            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private UploadedFileDto getUploadedFileDto(Map<?, ?> uploadResult) {
        UploadedFileDto uploadedFileDto = new UploadedFileDto();
        uploadedFileDto.setId((String) uploadResult.get("public_id"));
        uploadedFileDto.setUrl((String) uploadResult.get("secure_url"));

        return uploadedFileDto;
    }
}
