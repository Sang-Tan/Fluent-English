package com.fluentenglish.web.study.session.service.quiz.dto.resp;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IncorrectAnswerSubmissionResult extends AnswerSubmissionResult{
    @NotNull
    private String correctAnswer;

    @Override
    public int getScore() {
        return 0;
    }
}
