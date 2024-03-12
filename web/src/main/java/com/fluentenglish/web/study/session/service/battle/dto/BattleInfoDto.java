package com.fluentenglish.web.study.session.service.battle.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattleInfoDto {
    private int userCurrentHp;

    private int userMaxHp;

    private int userShield;

    private int userStreak;
}
