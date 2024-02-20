package com.fluentenglish.web.study.session.service.quiz.dto.resp;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "isCorrect")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CorrectAnswerSubmissionResult.class, name = "true"),
        @JsonSubTypes.Type(value = IncorrectAnswerSubmissionResult.class, name = "false")
})
public abstract class AnswerSubmissionResult {
    public abstract int getScore();
}
