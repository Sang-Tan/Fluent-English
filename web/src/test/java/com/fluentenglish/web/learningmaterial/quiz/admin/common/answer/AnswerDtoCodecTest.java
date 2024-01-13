package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.input.InputAnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.MultipleChoiceAnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.choice.TextChoices;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerDtoCodecTest {
    @Test
    void givenInputAnswer_testDeserialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("type", AnswerConstants.TYPE_INPUT);
        objectNode.put("answer", "answer text");
        objectNode.put("explanation", "explanation text");

        String json = objectMapper.writeValueAsString(objectNode);
        AnswerDto answerDto = objectMapper.readValue(json, AnswerDto.class);

        assertInstanceOf(InputAnswerDto.class, answerDto);
        InputAnswerDto inputAnswerDto = (InputAnswerDto) answerDto;
        assertEquals("answer text", inputAnswerDto.getAnswer());
        assertEquals("explanation text", inputAnswerDto.getExplanation());
    }

    @Test
    void givenInputAnswer_testSerialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputAnswerDto inputAnswerDto = new InputAnswerDto();
        inputAnswerDto.setAnswer("answer text");
        inputAnswerDto.setExplanation("explanation text");

        String json = objectMapper.writeValueAsString(inputAnswerDto);
        ObjectNode objectNode = objectMapper.readValue(json, ObjectNode.class);

        assertEquals(AnswerConstants.TYPE_INPUT, objectNode.get("type").asText());
        assertEquals("answer text", objectNode.get("answer").asText());
        assertEquals("explanation text", objectNode.get("explanation").asText());
    }

    @Test
    void givenMultipleChoiceAnswer_testDeserialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("type", AnswerConstants.TYPE_MULTIPLE_CHOICE);
        objectNode.put("correctChoice", 1);
        objectNode.put("explanation", "explanation text");

        ObjectNode choiceObj = objectNode.putObject("choices");
        choiceObj.put("type", "text");
        choiceObj.putArray("data").add("c1").add("c2");

        String json = objectMapper.writeValueAsString(objectNode);
        AnswerDto answerDto = objectMapper.readValue(json, AnswerDto.class);

        assertInstanceOf(MultipleChoiceAnswerDto.class, answerDto);
        MultipleChoiceAnswerDto multipleChoiceAnswerDto = (MultipleChoiceAnswerDto) answerDto;
        assertEquals(1, multipleChoiceAnswerDto.getCorrectChoice());
        assertEquals(2, multipleChoiceAnswerDto.getChoices().getData().size());
        assertEquals("explanation text", multipleChoiceAnswerDto.getExplanation());
    }

    @Test
    void givenMultipleChoiceAnswer_testSerialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MultipleChoiceAnswerDto multipleChoiceAnswerDto = new MultipleChoiceAnswerDto();
        multipleChoiceAnswerDto.setCorrectChoice(1);
        multipleChoiceAnswerDto.setExplanation("explanation text");

        TextChoices choices = new TextChoices();
        choices.getData().add("c1");
        choices.getData().add("c2");
        multipleChoiceAnswerDto.setChoices(choices);

        String json = objectMapper.writeValueAsString(multipleChoiceAnswerDto);
        ObjectNode objectNode = objectMapper.readValue(json, ObjectNode.class);

        assertEquals(AnswerConstants.TYPE_MULTIPLE_CHOICE, objectNode.get("type").asText());
        assertEquals(1, objectNode.get("correctChoice").asInt());
        assertEquals(2, objectNode.get("choices").get("data").size());
        assertEquals("explanation text", objectNode.get("explanation").asText());
    }
}
