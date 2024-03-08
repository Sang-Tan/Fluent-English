package com.fluentenglish.web.study.session.service.dto;

import com.fluentenglish.web.study.session.dao.battle.BattleUpdateInfo;
import com.fluentenglish.web.study.session.dao.quiz.Quiz;


public interface StudySessionStatusDto {
    Quiz getNextQuiz();
    void setNextQuiz(Quiz nextQuiz);
    BattleUpdateInfo getBattleUpdateInfo();
    void setBattleUpdateInfo(BattleUpdateInfo battleUpdateInfo);
    int getRemainingQuizzesCount();
    void setRemainingQuizzesCount(int remainingQuizzesCount);
}
