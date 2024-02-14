package com.fluentenglish.web.study.session.service.dto;

import com.fluentenglish.web.study.session.battle.BattleResult;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StudySessionSummary extends StudySessionInfo {
    private BattleResult battleResult;

    private Map<Integer, Integer> wordsScores;
}
