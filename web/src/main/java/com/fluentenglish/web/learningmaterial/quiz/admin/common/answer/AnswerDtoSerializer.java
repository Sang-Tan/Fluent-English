package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.input.InputAnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.MultipleChoiceAnswerDto;

import java.io.IOException;

public class AnswerDtoSerializer extends StdSerializer<AnswerDto> {
    protected AnswerDtoSerializer() {
        super(AnswerDto.class);
    }

    @Override
    public void serialize(AnswerDto value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", getType(value));
        gen.writeObject(value);
        gen.writeEndObject();
    }

    private String getType(AnswerDto value) {
        if (value instanceof InputAnswerDto) {
            return AnswerConstants.TYPE_INPUT;
        } else if (value instanceof MultipleChoiceAnswerDto) {
            return AnswerConstants.TYPE_MULTIPLE_CHOICE;
        } else {
            throw new IllegalArgumentException("Unknown answer type: " + value.getClass());
        }
    }
}
