package com.fluentenglish.web.study.session.service;

import com.fluentenglish.web.study.session.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.service.dto.StudySessionSubmissionDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionInitializationDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionUpdateDto;

import java.util.Set;

public interface StudySessionService {
    StudySessionInitializationDto startStudySession(int userId, Set<Integer> wordIds);

    StudySessionUpdateDto continueStudySession(String sessionId);

    boolean studySessionExists(String sessionId);

    StudySessionSubmissionDto submitAnswer(String sessionId, AnswerSubmission answer);
}
