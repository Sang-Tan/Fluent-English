package com.fluentenglish.web.upload.cloud;

import com.fluentenglish.web.upload.cloud.exception.UploadFileNotFoundException;

import java.io.InputStream;

public interface StorageService {

    /**
     * Uploads file to cloud storage, file name is generated automatically
     * @param inputStream file content
     * @param folder folder in which file will be stored
     * @return file id
     */
    String uploadFile(InputStream inputStream, String folder);

    /**
     * @throws UploadFileNotFoundException if file not found
     */
    String getFileUrl(String fileId);

    void deleteFile(String fileId);
}
