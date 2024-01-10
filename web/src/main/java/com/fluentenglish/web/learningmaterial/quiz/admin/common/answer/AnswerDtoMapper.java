package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerDtoMapper {
    default AnswerDto toAnswerDto(String answer) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(answer, AnswerDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default String toString(AnswerDto answerDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(answerDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
