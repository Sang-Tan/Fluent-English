package com.fluentenglish.web.study.session.battle;

import com.fluentenglish.web.gaming.user.dto.CurrentStateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BattleResult {
    private Integer userExp;
    private Integer userProgress;
    private CurrentStateDto currentState;
}
