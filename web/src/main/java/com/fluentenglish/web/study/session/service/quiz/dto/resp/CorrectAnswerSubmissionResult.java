package com.fluentenglish.web.study.session.service.quiz.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorrectAnswerSubmissionResult extends AnswerSubmissionResult{
    private int score;
    public CorrectAnswerSubmissionResult(int wordId, int score) {
        super(wordId);
        this.score = score;
    }
}
