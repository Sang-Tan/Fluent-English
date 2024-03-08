package com.fluentenglish.web.study.session.service.dto;

import com.fluentenglish.web.study.session.dao.battle.BattleInfo;
import com.fluentenglish.web.study.session.dao.battle.BattleUpdateInfo;
import com.fluentenglish.web.study.session.dao.quiz.Quiz;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudySessionActivationDto implements StudySessionStatusDto {
    private Quiz nextQuiz;
    private BattleInfo battleInfo;
    private int remainingQuizzesCount;
    private String sessionId;

    @Override
    public BattleUpdateInfo getBattleUpdateInfo() {
        return BattleUpdateInfo.builder()
                .userCurrentHp(battleInfo.getUserCurrentHp())
                .userMaxHp(battleInfo.getUserMaxHp())
                .userShield(battleInfo.getUserShield())
                .userStreak(battleInfo.getUserStreak())
                .build();
    }

    @Override
    public void setBattleUpdateInfo(BattleUpdateInfo battleUpdateInfo) {
        battleInfo.setUserCurrentHp(battleUpdateInfo.getUserCurrentHp());
        battleInfo.setUserMaxHp(battleUpdateInfo.getUserMaxHp());
        battleInfo.setUserShield(battleUpdateInfo.getUserShield());
        battleInfo.setUserStreak(battleUpdateInfo.getUserStreak());
    }
}
