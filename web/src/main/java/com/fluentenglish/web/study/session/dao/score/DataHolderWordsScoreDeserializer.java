package com.fluentenglish.web.study.session.dao.score;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class DataHolderWordsScoreDeserializer extends StdDeserializer<DataHolderWordsScores> {
    public DataHolderWordsScoreDeserializer() {
        super(DataHolderWordsScores.class);
    }

    @Override
    public DataHolderWordsScores deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        DataHolderWordsScores dataHolderWordsScores = new DataHolderWordsScores();
        while (p.nextToken() != null && !p.currentToken().isStructEnd()) {
            int key = Integer.parseInt(p.getText());
            p.nextToken();
            WordScore value = p.readValueAs(WordScore.class);
            dataHolderWordsScores.setWordScore(key, value);
        }
        return dataHolderWordsScores;
    }
}
