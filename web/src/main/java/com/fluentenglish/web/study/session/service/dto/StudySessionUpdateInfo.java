package com.fluentenglish.web.study.session.service.dto;

import com.fluentenglish.web.study.session.battle.BattleInfo;
import com.fluentenglish.web.study.session.quiz.Quiz;
import com.fluentenglish.web.study.session.quiz.dto.resp.AnswerSubmissionResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudySessionUpdateInfo extends StudySessionInfo {
    private Quiz nextQuiz;
    private BattleInfo battleInfo;
    private int remainingQuizzesCount;
    private AnswerSubmissionResult answerSubmissionResult;
}
