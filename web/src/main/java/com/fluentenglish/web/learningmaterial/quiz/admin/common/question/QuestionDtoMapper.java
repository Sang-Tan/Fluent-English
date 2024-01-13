package com.fluentenglish.web.learningmaterial.quiz.admin.common.question;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionDtoMapper {
    default QuestionDto toQuestionDto(String question) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(question, QuestionDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default String toString(QuestionDto questionDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(questionDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
