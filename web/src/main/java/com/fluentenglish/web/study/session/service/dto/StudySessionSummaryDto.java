package com.fluentenglish.web.study.session.service.dto;

import com.fluentenglish.web.study.session.service.battle.dto.BattleResult;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StudySessionSummaryDto extends StudySessionSubmissionDto {
    private BattleResult battleResult;

    private Map<Integer, Integer> wordsScores;
}
