package com.fluentenglish.web.learningmaterial.quiz.admin.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizDto {
    private Integer id;
    private Integer exerciseId;
    private JsonNode question;
    private JsonNode answer;
}
