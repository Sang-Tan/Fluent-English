package com.fluentenglish.web.study.session.service.battle;

import com.fluentenglish.web.study.session.service.battle.dto.BattleInfoDto;
import com.fluentenglish.web.study.session.service.battle.dto.BattleResult;

public interface BattleService {
    BattleInfoDto initializeBattle(String sessionId);

    BattleInfoDto updateBattle(String sessionId, Integer score);

    BattleInfoDto getBattleInfo(String sessionId);

    /**
     * @return battle result after battle ends
     */
    BattleResult endBattle(String sessionId);
}
