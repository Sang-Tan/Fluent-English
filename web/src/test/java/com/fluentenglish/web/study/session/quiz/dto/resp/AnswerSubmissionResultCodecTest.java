package com.fluentenglish.web.study.session.quiz.dto.resp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fluentenglish.web.study.session.service.quiz.dto.resp.AnswerSubmissionFailed;
import com.fluentenglish.web.study.session.service.quiz.dto.resp.CorrectAnswerSubmissionResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AnswerSubmissionResultCodecTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void serializeCorrectAnswerSubmissionResult() throws IOException {
        CorrectAnswerSubmissionResult correctAnswerSubmissionResult = new CorrectAnswerSubmissionResult(1, 10);

        String json = objectMapper.writeValueAsString(correctAnswerSubmissionResult);

        ObjectNode objectNode = objectMapper.readValue(json, ObjectNode.class);
        assertTrue(objectNode.get("isCorrect").isBoolean());
        assertTrue(objectNode.get("isCorrect").asBoolean());
        assertEquals(10, objectNode.get("score").asInt());
    }

    @Test
    void serializeIncorrectAnswerSubmissionResult() throws IOException {
        AnswerSubmissionFailed answerSubmissionFailed = new AnswerSubmissionFailed(1, "correctAnswer");

        String json = objectMapper.writeValueAsString(answerSubmissionFailed);

        ObjectNode objectNode = objectMapper.readValue(json, ObjectNode.class);
        assertTrue(objectNode.get("isCorrect").isBoolean());
        assertFalse(objectNode.get("isCorrect").asBoolean());
        assertEquals("correctAnswer", objectNode.get("correctAnswer").asText());
    }

}