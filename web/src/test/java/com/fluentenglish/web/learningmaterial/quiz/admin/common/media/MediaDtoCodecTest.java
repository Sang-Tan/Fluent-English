package com.fluentenglish.web.learningmaterial.quiz.admin.common.media;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class MediaDtoCodecTest {
    @Test
    void givenImageAttachment_testDeserialize() throws JsonProcessingException {
        JsonMapper jsonMapper = new JsonMapper();
        ObjectNode objectNode = jsonMapper.createObjectNode();
        objectNode.put("type", "image");
        objectNode.put("id", "IDIMG");
        objectNode.put("url", "url");
        String json = jsonMapper.writeValueAsString(objectNode);

        MediaDto mediaDto = jsonMapper.readValue(json, MediaDto.class);
        assertInstanceOf(ImageDto.class, mediaDto);
        ImageDto imageDto = (ImageDto) mediaDto;
        assertEquals("IDIMG", imageDto.getId());
        assertEquals("url", imageDto.getUrl());
    }

    @Test
    void givenImageAttachment_testSerialize() throws JsonProcessingException {
        JsonMapper jsonMapper = new JsonMapper();
        ImageDto imageDto = new ImageDto();
        imageDto.setId("IDIMG");
        imageDto.setUrl("url");
        String json = jsonMapper.writeValueAsString(imageDto);

        ObjectNode objectNode = jsonMapper.createObjectNode();
        objectNode.put("type", "image");
        objectNode.put("id", "IDIMG");
        objectNode.put("url", "url");
        String expectedJson = jsonMapper.writeValueAsString(objectNode);
        assertEquals(expectedJson, json);
    }

    @Test
    void givenAudioAttachment_testDeserialize() throws JsonProcessingException {
        JsonMapper jsonMapper = new JsonMapper();
        ObjectNode objectNode = jsonMapper.createObjectNode();
        objectNode.put("type", "audio");
        objectNode.put("id", "IDAUDIO");
        objectNode.put("url", "url");
        String json = jsonMapper.writeValueAsString(objectNode);

        MediaDto mediaDto = jsonMapper.readValue(json, MediaDto.class);
        assertInstanceOf(AudioDto.class, mediaDto);
        AudioDto audioDto = (AudioDto) mediaDto;
        assertEquals("IDAUDIO", audioDto.getId());
        assertEquals("url", audioDto.getUrl());
    }

    @Test
    void givenAudioAttachment_testSerialize() throws JsonProcessingException {
        JsonMapper jsonMapper = new JsonMapper();
        AudioDto audioDto = new AudioDto();
        audioDto.setId("IDAUDIO");
        audioDto.setUrl("url");
        String json = jsonMapper.writeValueAsString(audioDto);

        ObjectNode objectNode = jsonMapper.createObjectNode();
        objectNode.put("type", "audio");
        objectNode.put("id", "IDAUDIO");
        objectNode.put("url", "url");
        String expectedJson = jsonMapper.writeValueAsString(objectNode);
        assertEquals(expectedJson, json);
    }
}
