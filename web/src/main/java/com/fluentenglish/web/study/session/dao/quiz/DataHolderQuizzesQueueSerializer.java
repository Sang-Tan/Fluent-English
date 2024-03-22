package com.fluentenglish.web.study.session.dao.quiz;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class DataHolderQuizzesQueueSerializer extends StdSerializer<DataHolderQuizzesQueue> {

    public DataHolderQuizzesQueueSerializer() {
        super(DataHolderQuizzesQueue.class);
    }

    @Override
    public void serialize(DataHolderQuizzesQueue value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getQuizzes());
    }
}
