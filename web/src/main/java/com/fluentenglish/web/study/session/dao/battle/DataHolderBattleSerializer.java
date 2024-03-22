package com.fluentenglish.web.study.session.dao.battle;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class DataHolderBattleSerializer extends StdSerializer<DataHolderBattle> {

    public DataHolderBattleSerializer() {
        super(DataHolderBattle.class);
    }

    @Override
    public void serialize(DataHolderBattle value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getBattleInfo());
    }
}
