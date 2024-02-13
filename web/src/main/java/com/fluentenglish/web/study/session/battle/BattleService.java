package com.fluentenglish.web.study.session.battle;

public interface BattleService {
    BattleInfo initializeBattle(String sessionId);

    BattleInfo updateBattle(String sessionId, Integer score);

    /**
     * @return battle result after battle ends
     */
    BattleResult endBattle(String sessionId);
}
