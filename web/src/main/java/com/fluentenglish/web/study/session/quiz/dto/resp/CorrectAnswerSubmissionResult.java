package com.fluentenglish.web.study.session.quiz.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CorrectAnswerSubmissionResult extends AnswerSubmissionResult{
    private int score;
}
