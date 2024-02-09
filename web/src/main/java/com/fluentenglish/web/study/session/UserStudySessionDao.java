package com.fluentenglish.web.study.session;

import com.fluentenglish.web.study.session.exception.StudySessionExistsException;
import com.fluentenglish.web.study.session.exception.StudySessionNotExistException;

public interface UserStudySessionDao {

    /**
     * @throws StudySessionExistsException if a study session already exists for the user
     */
    StudySession createSession(int userId);

    /**
     * @throws StudySessionNotExistException if a study session does not exist for the user
     */
    StudySession getSessionByUserId(int userId);

    /**
     * @throws StudySessionNotExistException if a study session does not exist for the user
     */
    StudySession getSessionById(String sessionId);

    boolean studySessionOfUserExists(int userId);

    boolean studySessionExists(String sessionId);

    void deleteStudySession(String sessionId);
}
