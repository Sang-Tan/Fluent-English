package com.fluentenglish.web.upload.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.exceptions.BadRequest;
import com.fluentenglish.web.upload.cloud.exception.UploadFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class CloudinaryStorageService implements StorageService {
    private final Cloudinary cloudinary;

    public CloudinaryStorageService(@Value("${upload.cloudinary.url}") String cloudinaryUrl) {
        this.cloudinary = new Cloudinary(cloudinaryUrl);
    }

    @Override
    public UploadedFileDto uploadFile(UploadDto uploadDto) {
        Map<String, Object> params = new HashMap<>();
        String folder = uploadDto.getFolder();
        if (folder != null) {
            params.put("folder", folder);
        }
        params.put("resource_type", getResourceType(uploadDto));

        File tempFile = createTempFile(uploadDto.getInputStream(), uploadDto.getExtension());
        try {
            Map<?, ?> uploadResult =
                    cloudinary.uploader().upload(tempFile, params);
            tempFile.delete();
            return getUploadedFileDto(uploadResult);
        } catch (IOException e) {
            tempFile.delete();
            throw new RuntimeException(e);
        }
    }

    @Override
    public UploadedFileDto getFileData(String fileId) {
        try {
            Map<?, ?> result = cloudinary.api().resourceByAssetID(fileId, Map.of());
            return getUploadedFileDto(result);
        } catch (BadRequest e) {
            throw new UploadFileNotFoundException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getResourceType(UploadDto uploadDto) {
        String mimeType = uploadDto.getMimeType();
        if (mimeType.startsWith("image")) {
            return "image";
        } else if (mimeType.startsWith("video")) {
            return "video";
        } else {
            return "auto";
        }
    }

    @Override
    public void deleteFile(String fileId) {
        try {
            cloudinary.uploader().destroy(fileId, Map.of());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File createTempFile(InputStream inputStream, String fileExtension) {
        try {
            File tempFile = File.createTempFile("temp", fileExtension == null ? "" : "." + fileExtension);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
            inputStream.transferTo(outputStream);

            outputStream.close(); //close and flush remaining data into file

            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private UploadedFileDto getUploadedFileDto(Map<?, ?> uploadResult) {
        UploadedFileDto uploadedFileDto = new UploadedFileDto();
        uploadedFileDto.setId((String) uploadResult.get("asset_id"));
        uploadedFileDto.setUrl((String) uploadResult.get("secure_url"));

        return uploadedFileDto;
    }
}
