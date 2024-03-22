package com.fluentenglish.web.study.session.dao.lastinteraction;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class DataHolderLastInteractionDeserializer extends StdDeserializer<DataHolderLastInteraction> {
    public DataHolderLastInteractionDeserializer() {
        super(DataHolderLastInteraction.class);
    }

    @Override
    public DataHolderLastInteraction deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        DataHolderLastInteraction dataHolderLastInteraction = new DataHolderLastInteraction();
        dataHolderLastInteraction.setLastInteractionTime(p.readValueAs(Long.class));
        return dataHolderLastInteraction;
    }


}
