package com.fluentenglish.web.study.session.quiz.answer.multiplechoice.choice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AnswerChoicesCodecTest {
    @Test
    void testTextChoicesSerialize() throws JsonProcessingException {
        TextAnswerChoices choices = new TextAnswerChoices();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(choices);

        ObjectNode node = mapper.readValue(json, ObjectNode.class);

        assertEquals("text", node.get("type").asText());
        assertEquals(0, node.get("data").size());
    }

    @Test
    void testTextChoicesDeserialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "text");
        node.putArray("data");
        String json = mapper.writeValueAsString(node);

        AnswerChoices<?> choices = mapper.readValue(json, AnswerChoices.class);

        assertInstanceOf(TextAnswerChoices.class, choices);
        assertEquals(0, choices.data.size());
    }

    @Test
    void testAudioChoicesSerialize() throws JsonProcessingException {
        AudioAnswerChoices choices = new AudioAnswerChoices();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(choices);

        ObjectNode node = mapper.readValue(json, ObjectNode.class);

        assertEquals("audio", node.get("type").asText());
        assertEquals(0, node.get("data").size());
    }

    @Test
    void testAudioChoicesDeserialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "audio").putArray("data");
        String json = mapper.writeValueAsString(node);

        AnswerChoices<?> choices = mapper.readValue(json, AnswerChoices.class);

        assertInstanceOf(AudioAnswerChoices.class, choices);
        assertEquals(0, choices.data.size());
    }

}