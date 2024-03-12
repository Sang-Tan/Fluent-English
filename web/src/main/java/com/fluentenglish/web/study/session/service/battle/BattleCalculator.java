package com.fluentenglish.web.study.session.service.battle;

import org.springframework.stereotype.Component;

@Component
public class BattleCalculator {
    public int calculateDamageTaken(int userStreak, int score, int enemyDmg) {
        return Math.toIntExact(Math.round(enemyDmg * (1 - userStreak * 0.1 + (10 - score) * 0.1)));
    }
}
