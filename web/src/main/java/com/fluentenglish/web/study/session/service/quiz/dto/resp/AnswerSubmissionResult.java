package com.fluentenglish.web.study.session.service.quiz.dto.resp;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "isCorrect")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CorrectAnswerSubmissionResult.class, name = "true"),
        @JsonSubTypes.Type(value = AnswerSubmissionFailed.class, name = "false")
})
@Getter
@Setter
public abstract class AnswerSubmissionResult {
    private int wordId;

    public AnswerSubmissionResult(int wordId) {
        this.wordId = wordId;
    }

    public abstract int getScore();
}
