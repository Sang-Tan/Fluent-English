package com.fluentenglish.web.common.fileresource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileResourceCodecTest {
    @Test
    public void testDecodeCloudinary() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = cloudinaryFileResourceJson();

        FileResourceDto fileResourceDto = objectMapper.readValue(json, FileResourceDto.class);

        assertInstanceOf(CloudinaryFileResourceDto.class, fileResourceDto);
        CloudinaryFileResourceDto cloudinaryFileResourceDto =
                (CloudinaryFileResourceDto) fileResourceDto;
        assertEquals("example.com", cloudinaryFileResourceDto.getUrl());
        assertEquals("publicId", cloudinaryFileResourceDto.getPublicId());
    }

    @Test
    public void testDecodeExternal() throws JsonProcessingException {
        String json = externalFileResourceJson(null);
        ObjectMapper objectMapper = new ObjectMapper();

        FileResourceDto fileResourceDto = objectMapper.readValue(json, FileResourceDto.class);
        assertInstanceOf(ExternalFileResourceDto.class, fileResourceDto);

        ExternalFileResourceDto externalFileResourceDto =
                (ExternalFileResourceDto) fileResourceDto;
        assertEquals("example.com", externalFileResourceDto.getUrl());
    }

    @Test
    public void testDecodeMediaType() throws JsonProcessingException {
        String json = externalFileResourceJson(MediaType.IMAGE.getType());
        ObjectMapper objectMapper = new ObjectMapper();

        FileResourceDto fileResourceDto = objectMapper.readValue(json, FileResourceDto.class);

        assertEquals(MediaType.IMAGE, fileResourceDto.getMediaType());
    }

    @Test
    public void testEncodeCloudinary() throws JsonProcessingException {
        CloudinaryFileResourceDto cloudinaryFileResourceDto = cloudinaryFileResourceDto();
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(cloudinaryFileResourceDto);

        ObjectNode root = objectMapper.readValue(json, ObjectNode.class);
        assertEquals("publicId", root.get("publicId").asText());
        assertEquals("example.com", root.get("url").asText());
    }

    @Test
    public void testEncodeExternal() throws JsonProcessingException {
        ExternalFileResourceDto externalFileResourceDto = externalFileResourceDto(MediaType.IMAGE);
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(externalFileResourceDto);

        ObjectNode root = objectMapper.readValue(json, ObjectNode.class);
        assertEquals("example.com", root.get("url").asText());
        assertEquals(MediaType.IMAGE.getType(), root.get("mediaType").asText());
    }

    @Test
    public void testEncodeNotIncludeNullMediaType() throws JsonProcessingException {
        ExternalFileResourceDto externalFileResourceDto = externalFileResourceDto(null);
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(externalFileResourceDto);

        ObjectNode root = objectMapper.readValue(json, ObjectNode.class);
        assertFalse(root.has("mediaType"));
    }

    private String cloudinaryFileResourceJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();

        root.put("type", ResourceStorages.CLOUDINARY);
        root.put("publicId", "publicId");
        root.put("url", "example.com");

        return root.toString();
    }

    private String externalFileResourceJson(String mediaType) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode root = objectMapper.createObjectNode();
        root.put("type", ResourceStorages.EXTERNAL);
        root.put("url", "example.com");
        root.put("mediaType", mediaType);

        return root.toString();
    }

    private CloudinaryFileResourceDto cloudinaryFileResourceDto() {
        CloudinaryFileResourceDto cloudinaryFileResourceDto = new CloudinaryFileResourceDto();
        cloudinaryFileResourceDto.setPublicId("publicId");
        cloudinaryFileResourceDto.setUrl("example.com");

        return cloudinaryFileResourceDto;
    }

    private ExternalFileResourceDto externalFileResourceDto(MediaType mediaType) {
        ExternalFileResourceDto externalFileResourceDto = new ExternalFileResourceDto();
        externalFileResourceDto.setUrl("example.com");
        externalFileResourceDto.setMediaType(mediaType);

        return externalFileResourceDto;
    }
}
