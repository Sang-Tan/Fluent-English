package com.fluentenglish.web.study.session.dao.lastinteraction;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class DataHolderLastInteractionSerializer extends StdSerializer<DataHolderLastInteraction> {
    public DataHolderLastInteractionSerializer() {
        super(DataHolderLastInteraction.class);
    }


    @Override
    public void serialize(DataHolderLastInteraction value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getLastInteractionTime());
    }
}
