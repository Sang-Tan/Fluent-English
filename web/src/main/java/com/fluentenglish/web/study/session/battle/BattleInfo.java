package com.fluentenglish.web.study.session.battle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BattleInfo {
    private int userHp;

    private int userShield;

    private int userStreak;

    private int userBaseDmg;

    private String enemyName;

    private int enemyDmg;
}
