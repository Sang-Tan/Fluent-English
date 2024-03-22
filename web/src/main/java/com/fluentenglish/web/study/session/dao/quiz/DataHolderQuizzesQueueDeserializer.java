package com.fluentenglish.web.study.session.dao.quiz;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.LinkedList;

public class DataHolderQuizzesQueueDeserializer extends StdDeserializer<DataHolderQuizzesQueue> {
    public DataHolderQuizzesQueueDeserializer() {
        super(DataHolderQuizzesQueue.class);
    }

    @Override
    public DataHolderQuizzesQueue deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        LinkedList<Quiz> quizzes = new LinkedList<>();
        while (p.nextToken() != null) {
            if (p.getCurrentToken() == JsonToken.START_OBJECT) {
                quizzes.add(p.readValueAs(Quiz.class));
            }
        }
        DataHolderQuizzesQueue dataHolderQuizzesQueue = new DataHolderQuizzesQueue();
        dataHolderQuizzesQueue.add(quizzes);
        return dataHolderQuizzesQueue;
    }
}
