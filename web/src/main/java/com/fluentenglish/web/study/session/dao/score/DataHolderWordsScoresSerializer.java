package com.fluentenglish.web.study.session.dao.score;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class DataHolderWordsScoresSerializer extends StdSerializer<DataHolderWordsScores> {
    public DataHolderWordsScoresSerializer() {
        super(DataHolderWordsScores.class);
    }

    @Override
    public void serialize(DataHolderWordsScores value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getWordsScores());
    }
}
