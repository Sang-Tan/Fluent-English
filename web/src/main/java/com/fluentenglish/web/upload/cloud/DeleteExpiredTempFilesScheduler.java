package com.fluentenglish.web.upload.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DeleteExpiredTempFilesScheduler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final long maxAgeMs;

    private final CloudinaryStorageService cloudinaryStorageService;

    private final TemporaryFileRepository temporaryFileRepository;

    public DeleteExpiredTempFilesScheduler(@Value("${upload.cloudinary.temporary-file.max-age-ms}") long maxAgeMs,
                                           CloudinaryStorageService cloudinaryStorageService,
                                           TemporaryFileRepository temporaryFileRepository) {
        this.maxAgeMs = maxAgeMs;
        this.cloudinaryStorageService = cloudinaryStorageService;
        this.temporaryFileRepository = temporaryFileRepository;
    }

    @Scheduled(fixedRateString = "${upload.cloudinary.temporary-file.delete-interval-ms}",
            initialDelayString = "${upload.cloudinary.temporary-file.initial-delay-ms}")
    private void deleteTemporaryFiles() {
        logger.info("Deleting expired temporary files");

        Date maxAgeDate = new Date(System.currentTimeMillis() - maxAgeMs);
        List<TemporaryFile> temporaryFiles = temporaryFileRepository.findAllByUploadDateBefore(maxAgeDate);
        cloudinaryStorageService.deleteFiles(temporaryFiles.stream().map(TemporaryFile::getId).toList());

        logger.info("Deleted {} expired temporary files", temporaryFiles.size());
    }
}
