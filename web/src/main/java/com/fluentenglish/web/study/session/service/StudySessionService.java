package com.fluentenglish.web.study.session.service;

import com.fluentenglish.web.study.session.service.dto.StudySessionActivationDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionSubmissionDto;
import com.fluentenglish.web.study.session.service.exception.SessionActiveException;
import com.fluentenglish.web.study.session.service.quiz.dto.AnswerSubmission;

import java.util.Set;

public interface StudySessionService {
    StudySessionActivationDto startStudySession(int userId, Set<Integer> wordIds);

    /**
     * @throws SessionActiveException if the session is currently active
     */
    StudySessionActivationDto continueStudySession(int userId);

    boolean studySessionExists(String sessionId);

    StudySessionSubmissionDto submitAnswer(String sessionId, AnswerSubmission answer);

    /**
     * User failed to provide answer
     */
    StudySessionSubmissionDto handleFailedAnswerSubmission(String sessionId);
}
