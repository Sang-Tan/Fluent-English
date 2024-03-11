package com.fluentenglish.web.study.session.service.battle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BattleInfoDto {
    private int userCurrentHp;

    private int userMaxHp;

    private int userShield;

    private int userStreak;
}
