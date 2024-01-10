package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.choice.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AnswerChoicesCodecTest {
    @Test
    void givenTextChoices_testDeserialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();
        root.put("type", ChoiceConstants.TYPE_TEXT);
        root.putArray("data").add("c1");

        String json = objectMapper.writeValueAsString(root);

        AnswerChoices<?> choices = objectMapper.readValue(json, AnswerChoices.class);

        assertInstanceOf(TextChoices.class, choices);
        assertEquals(1, ((TextChoices) choices).getData().size());
        assertEquals("c1", ((TextChoices) choices).getData().get(0));
    }

    @Test
    void givenTextChoiceList_testSerialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TextChoices choices = new TextChoices();
        choices.getData().add("c1");
        String json = objectMapper.writeValueAsString(choices);

        ObjectNode root = objectMapper.createObjectNode();
        root.put("type", ChoiceConstants.TYPE_TEXT);
        root.putArray("data").add("c1");
        String expectedJson = objectMapper.writeValueAsString(root);
        assertEquals(expectedJson, json);
    }

    @Test
    void givenAudioChoiceList_testDeserialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();
        root.put("type", ChoiceConstants.TYPE_AUDIO);

        ArrayNode dataArr = root.putArray("data");
        dataArr.addObject().put("id", "id1").put("url", "url1");

        String json = objectMapper.writeValueAsString(root);
        AnswerChoices<?> choices = objectMapper.readValue(json, AnswerChoices.class);

        assertInstanceOf(AudioChoices.class, choices);
        assertEquals(1, ((AudioChoices) choices).getData().size());
        assertEquals("id1", ((AudioChoices) choices).getData().get(0).getId());
        assertEquals("url1", ((AudioChoices) choices).getData().get(0).getUrl());
    }

    @Test
    void givenAudioChoiceList_testSerialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AudioChoices choices = new AudioChoices();

        AudioChoiceDto audioChoiceDto = new AudioChoiceDto();
        audioChoiceDto.setId("id1");
        audioChoiceDto.setUrl("url1");
        choices.getData().add(audioChoiceDto);

        ObjectNode root = objectMapper.createObjectNode();
        root.put("type", ChoiceConstants.TYPE_AUDIO);

        ArrayNode dataArr = root.putArray("data");
        dataArr.addObject().put("id", "id1").put("url", "url1");

        String json = objectMapper.writeValueAsString(choices);
        String expectedJson = objectMapper.writeValueAsString(root);
        assertEquals(expectedJson, json);
    }
}
