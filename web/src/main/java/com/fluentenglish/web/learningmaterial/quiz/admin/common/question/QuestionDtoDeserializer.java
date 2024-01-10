package com.fluentenglish.web.learningmaterial.quiz.admin.common.question;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.media.MediaDto;

import java.io.IOException;

public class QuestionDtoDeserializer extends StdDeserializer<QuestionDto> {
    protected QuestionDtoDeserializer() {
        super(QuestionDto.class);
    }

    protected QuestionDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public QuestionDto deserialize(JsonParser p, DeserializationContext context) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        QuestionDto questionDto = new QuestionDto();
        questionDto.setContent(node.get("content").asText());
        if (node.get("attachment") != null) {
            questionDto.setAttachment(p.getCodec().treeToValue(node.get("attachment"), MediaDto.class));
        }

        return questionDto;
    }
}
