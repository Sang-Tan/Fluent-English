package com.fluentenglish.web.study.session.dao.score;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataHolderWordsScoreDeserializerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(DataHolderWordsScores.class, new DataHolderWordsScoreDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    void deserialize() throws JsonProcessingException {
        ObjectNode myMapNode = objectMapper.createObjectNode();
        myMapNode.set("1", objectMapper.nullNode());
        myMapNode.set("2", objectMapper.nullNode());
        myMapNode.set("3", objectMapper.nullNode());

        String json = myMapNode.toString();

        DataHolderWordsScores dataHolderWordsScores = objectMapper.readValue(json, DataHolderWordsScores.class);

        assertEquals(3, dataHolderWordsScores.getWordsScores().size());
        assertTrue(dataHolderWordsScores.getWordsScores().containsKey(1));
        assertTrue(dataHolderWordsScores.getWordsScores().containsKey(2));
        assertTrue(dataHolderWordsScores.getWordsScores().containsKey(3));
    }
}