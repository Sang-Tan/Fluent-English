package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.input.InputAnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.MultipleChoiceAnswerDto;

import java.io.IOException;

public class AnswerDtoDeserializer extends StdDeserializer<AnswerDto> {
    protected AnswerDtoDeserializer() {
        super(AnswerDto.class);
    }

    @Override
    public AnswerDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectNode node = p.getCodec().readTree(p);
        String type = node.get("type").asText();
        node.remove("type");
        if (AnswerConstants.TYPE_INPUT.equals(type)) {
            return p.getCodec().treeToValue(node, InputAnswerDto.class);
        } else if (AnswerConstants.TYPE_MULTIPLE_CHOICE.equals(type)) {
            return p.getCodec().treeToValue(node, MultipleChoiceAnswerDto.class);
        } else {
            throw new IllegalArgumentException("Unknown answer type: " + type);
        }
    }
}
