package com.fluentenglish.web.upload.cloud;

import com.fluentenglish.web.upload.cloud.exception.UploadFileNotFoundException;

import java.io.InputStream;

public interface StorageService {

    /**
     * Uploads file to cloud storage, file name is generated automatically
     */
    UploadedFileDto uploadFile(UploadDto uploadDto);

    /**
     * @throws UploadFileNotFoundException if file not found
     */
    UploadedFileDto getFileData(String fileId);

    void deleteFile(String fileId);
}
