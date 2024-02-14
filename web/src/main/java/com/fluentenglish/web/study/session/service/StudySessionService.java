package com.fluentenglish.web.study.session.service;

import com.fluentenglish.web.study.session.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.service.dto.StudySessionInfo;
import com.fluentenglish.web.study.session.service.dto.StudySessionUpdateInfo;

import java.util.Set;

public interface StudySessionService {
    StudySessionUpdateInfo startStudySession(int userId, Set<Integer> wordIds);

    StudySessionUpdateInfo continueStudySession(String sessionId);

    boolean studySessionExists(String sessionId);

    StudySessionInfo submitAnswer(String sessionId, AnswerSubmission answer);
}
