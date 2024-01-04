package com.fluentenglish.web.upload.cloud;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
public class UploadDto {
    private InputStream inputStream;

    private String folder;

    private String mimeType;

    private String extension;
}
