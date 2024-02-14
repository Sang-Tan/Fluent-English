package com.fluentenglish.web.study.session.battle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BattleInfo {
    private int userHp;

    private int userShield;

    private int userStreak;

    private String enemyName;

    private int enemyDmg;

    private int expGain;
}
