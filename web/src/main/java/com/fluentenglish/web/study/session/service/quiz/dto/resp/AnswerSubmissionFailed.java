package com.fluentenglish.web.study.session.service.quiz.dto.resp;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerSubmissionFailed extends AnswerSubmissionResult{
    @NotNull
    private String correctAnswer;

    public AnswerSubmissionFailed(int wordId, String correctAnswer) {
        super(wordId);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public int getScore() {
        return 0;
    }
}
