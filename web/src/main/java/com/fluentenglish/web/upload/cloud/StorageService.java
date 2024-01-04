package com.fluentenglish.web.upload.cloud;

import com.fluentenglish.web.upload.cloud.exception.UploadFileNotFoundException;

import java.io.InputStream;
import java.util.List;

public interface StorageService {

    /**
     * Uploads file to cloud storage, file name is generated automatically
     */
    UploadedFileDto uploadFile(UploadDto uploadDto);

    /**
     * Uploads file to cloud storage, file name is generated automatically,
     * file is stored temporarily and will be deleted after some time
     */
    UploadedFileDto uploadFileTemp(UploadDto uploadDto);

    /**
     * Makes temporary file permanent
     */
    void makeFilePermanent(String fileId);

    /**
     * @throws UploadFileNotFoundException if file not found
     */
    UploadedFileDto getFileData(String fileId);

    void deleteFile(String fileId);

    void deleteFiles(List<String> fileIds);
}
