package com.fluentenglish.web.study.session.service.quiz.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AnswerSubmissionResult {
    private int wordId;

    public AnswerSubmissionResult(int wordId) {
        this.wordId = wordId;
    }

    public abstract int getScore();

    /**
     * Workaround for polymorphism in Jackson
     */
    @JsonProperty("isCorrect")
    public abstract boolean isCorrect();
}
