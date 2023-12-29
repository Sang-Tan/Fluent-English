package com.fluentenglish.web.upload.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Primary
@Service
public class CloudinaryServiceProxy implements StorageService {
    private final CloudinaryStorageService cloudinaryStorageService;

    public CloudinaryServiceProxy(CloudinaryStorageService cloudinaryStorageService) {
        this.cloudinaryStorageService = cloudinaryStorageService;
    }

    @Override
    public UploadedFileDto uploadFile(UploadDto uploadDto) {
        UploadedFileDto uploadedFileDto = cloudinaryStorageService.uploadFile(uploadDto);
        uploadedFileDto.setId(encodePublicId(uploadedFileDto.getId()));

        return uploadedFileDto;
    }

    @Override
    public UploadedFileDto uploadFileTemp(UploadDto uploadDto) {
        UploadedFileDto uploadedFileDto = cloudinaryStorageService.uploadFileTemp(uploadDto);
        uploadedFileDto.setId(encodePublicId(uploadedFileDto.getId()));

        return uploadedFileDto;
    }

    @Override
    public void makeFilePermanent(String fileId) {
        cloudinaryStorageService.makeFilePermanent(decodePublicId(fileId));
    }

    @Override
    public UploadedFileDto getFileData(String fileId) {
        UploadedFileDto uploadedFileDto = cloudinaryStorageService.getFileData(decodePublicId(fileId));
        uploadedFileDto.setId(encodePublicId(uploadedFileDto.getId()));

        return uploadedFileDto;
    }

    @Override
    public void deleteFile(String fileId) {
        cloudinaryStorageService.deleteFile(decodePublicId(fileId));
    }

    @Override
    public void deleteFiles(List<String> fileIds) {
        cloudinaryStorageService.deleteFiles(decodePublicIds(fileIds));
    }

    private String encodePublicId(String publicId) {
        return Base64.getEncoder().encodeToString(publicId.getBytes());
    }

    private List<String> decodePublicIds(List<String> encodedPublicIds) {
        List<String> publicIds = new ArrayList<>();
        for (String encodedPublicId : encodedPublicIds) {
            publicIds.add(decodePublicId(encodedPublicId));
        }
        return publicIds;
    }

    private String decodePublicId(String encodedPublicId) {
        return new String(Base64.getDecoder().decode(encodedPublicId));
    }
}
