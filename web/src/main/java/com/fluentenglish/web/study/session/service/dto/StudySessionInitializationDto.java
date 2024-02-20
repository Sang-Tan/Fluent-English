package com.fluentenglish.web.study.session.service.dto;

import com.fluentenglish.web.study.session.battle.BattleInfo;
import com.fluentenglish.web.study.session.quiz.Quiz;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudySessionInitializationDto implements StudySessionStatusDto {
    private Quiz nextQuiz;
    private BattleInfo battleInfo;
    private int remainingQuizzesCount;
    private String sessionId;
}
