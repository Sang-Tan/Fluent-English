package com.fluentenglish.web.study.session.dao.battle;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattleInfo {
    private int userCurrentHp;

    private int userMaxHp;

    private int userShield;

    private int userStreak;

    private String enemyName;

    private int enemyDmg;

    private int expGain;
}
