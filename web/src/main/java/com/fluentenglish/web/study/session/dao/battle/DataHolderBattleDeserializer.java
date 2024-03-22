package com.fluentenglish.web.study.session.dao.battle;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class DataHolderBattleDeserializer extends StdDeserializer<DataHolderBattle> {
    public DataHolderBattleDeserializer() {
        super(DataHolderBattle.class);
    }

    @Override
    public DataHolderBattle deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        DataHolderBattle dataHolderBattle = new DataHolderBattle();
        dataHolderBattle.setBattleInfo(p.readValueAs(BattleInfo.class));
        return dataHolderBattle;
    }
}
