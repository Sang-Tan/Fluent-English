package com.fluentenglish.web.study.session.dao.battle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataHolderBattle implements SessionBattle {
    private BattleInfo battleInfo;
}
