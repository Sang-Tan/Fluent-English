package com.fluentenglish.web.upload.cloud;

import com.fluentenglish.web.upload.cloud.dto.UploadDto;
import com.fluentenglish.web.upload.cloud.exception.UploadFileNotFoundException;

import java.util.List;

public interface MediaUploadService<ID, T> {

    /**
     * Uploads file to cloud storage, file name is generated automatically
     */
    T uploadFile(UploadDto uploadDto);

    /**
     * @throws UploadFileNotFoundException if file not found
     */
    T getFileData(ID fileId);

    void deleteFile(ID fileId);

    void deleteFiles(List<ID> fileIds);
}
