package com.fluentenglish.web.study.session.service.dto;

import com.fluentenglish.web.study.session.dao.quiz.Quiz;
import com.fluentenglish.web.study.session.service.battle.dto.BattleInfoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudySessionActivationDto implements StudySessionStatusDto {
    private Quiz nextQuiz;
    private BattleInfoDto battleInfo;
    private int remainingQuizzesCount;
    /**
     * this is session access id
     */
    private String sessionId;
}
