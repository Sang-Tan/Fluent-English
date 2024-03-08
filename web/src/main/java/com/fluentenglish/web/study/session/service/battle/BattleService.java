package com.fluentenglish.web.study.session.service.battle;

import com.fluentenglish.web.study.session.dao.battle.BattleInfo;
import com.fluentenglish.web.study.session.service.battle.dto.BattleResult;

public interface BattleService {
    BattleInfo initializeBattle(String sessionId);

    BattleInfo updateBattle(String sessionId, Integer score);

    BattleInfo getBattleInfo(String sessionId);

    /**
     * @return battle result after battle ends
     */
    BattleResult endBattle(String sessionId);
}
