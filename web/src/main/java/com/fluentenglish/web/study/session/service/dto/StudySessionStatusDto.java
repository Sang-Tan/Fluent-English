package com.fluentenglish.web.study.session.service.dto;

import com.fluentenglish.web.study.session.dao.quiz.Quiz;
import com.fluentenglish.web.study.session.service.battle.dto.BattleInfoDto;


public interface StudySessionStatusDto {
    Quiz getNextQuiz();

    void setNextQuiz(Quiz nextQuiz);

    BattleInfoDto getBattleInfo();

    void setBattleInfo(BattleInfoDto battleInfoDto);

    int getRemainingQuizzesCount();

    void setRemainingQuizzesCount(int remainingQuizzesCount);
}
