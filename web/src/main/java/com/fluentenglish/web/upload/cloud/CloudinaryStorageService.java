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
    public UploadedFileDto uploadFile(InputStream inputStream, String folder) {
        try {
            Map<String, Object> params = new HashMap<>();
            if (folder != null) {
                params.put("folder", folder);
            }
            params.put("resource_type", "auto");

            File tempFile = createTempFile(inputStream);
            Map<?, ?> uploadResult =
                    cloudinary.uploader().upload(tempFile, params);
            tempFile.delete();

            return getUploadedFileDto(uploadResult);
        } catch (IOException e) {
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
        }
        catch (Exception e) {
            throw new RuntimeException(e);
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

    private File createTempFile(InputStream inputStream) throws IOException {
        File tempFile = File.createTempFile("temp", null);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
        inputStream.transferTo(outputStream);

        return tempFile;
    }

    private UploadedFileDto getUploadedFileDto(Map<?, ?> uploadResult) {
        UploadedFileDto uploadedFileDto = new UploadedFileDto();
        uploadedFileDto.setId((String) uploadResult.get("asset_id"));
        uploadedFileDto.setUrl((String) uploadResult.get("secure_url"));

        return uploadedFileDto;
    }
}
