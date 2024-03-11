package com.fluentenglish.web.study.session.dao;

import com.fluentenglish.web.study.session.dao.exception.StudySessionExistsException;
import com.fluentenglish.web.study.session.dao.exception.StudySessionNotExistException;

public interface StudySessionDao {

    /**
     * @throws StudySessionExistsException if a study session already exists for the user
     */
    StudySession createSession(int userId);

    /**
     * @throws StudySessionNotExistException if a study session does not exist for the user
     */
    StudySession getSessionById(String sessionId);

    /**
     * @throws StudySessionNotExistException if a study session does not exist for the user
     */
    String getSessionIdByUserId(int userId);

    boolean studySessionOfUserExists(int userId);

    boolean studySessionExists(String sessionId);

    void deleteStudySession(String sessionId);
}
