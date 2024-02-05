package com.fluentenglish.web.spacedrepetition.session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerQuizDto {
    private Integer quizId;
    private Integer timeAnsweredMs;
    private boolean isCorrect;
}
