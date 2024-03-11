package com.fluentenglish.web.study.session.service.dto;

import com.fluentenglish.web.study.session.dao.quiz.Quiz;
import com.fluentenglish.web.study.session.service.battle.dto.BattleInfoDto;
import com.fluentenglish.web.study.session.service.quiz.dto.resp.AnswerSubmissionResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudySessionUpdateDto extends StudySessionSubmissionDto implements StudySessionStatusDto {
    private AnswerSubmissionResult answerSubmissionResult;
    private Quiz nextQuiz;
    private BattleInfoDto battleInfo;
    private int remainingQuizzesCount;
}
