package com.fluentenglish.web.study.session.service.quiz.dto.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorrectAnswerSubmissionResult extends AnswerSubmissionResult {
    private int score;

    public CorrectAnswerSubmissionResult(int wordId, int score) {
        super(wordId);
        this.score = score;
    }

    @Override
    public boolean isCorrect() {
        return true;
    }
}
