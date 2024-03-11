package com.fluentenglish.web.study.session.service.interaction;

import com.fluentenglish.web.study.session.dao.StudySession;
import com.fluentenglish.web.study.session.dao.StudySessionDao;
import com.fluentenglish.web.study.session.dao.lastinteraction.SessionLastInteraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StudySessionInteractionServiceImpl implements StudySessionInteractionService {
    private final StudySessionDao studySessionDao;

    private final int onlineLifespanSec;

    public StudySessionInteractionServiceImpl(@Value("${study-session.online-lifespan-secs}") int onlineLifespanSec,
                                              StudySessionDao studySessionDao) {
        this.onlineLifespanSec = onlineLifespanSec;
        this.studySessionDao = studySessionDao;
    }

    @Override
    public void setLastInteractionTime(String sessionId, long lastInteractionTimeMs) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
        SessionLastInteraction sessionLastInteraction = studySession.getLastInteraction();

        sessionLastInteraction.setLastInteractionTime(lastInteractionTimeMs);
    }

    @Override
    public long getLastInteractionTime(String sessionId) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
        SessionLastInteraction sessionLastInteraction = studySession.getLastInteraction();

        return sessionLastInteraction.getLastInteractionTime();
    }

    @Override
    public boolean isSessionActive(String sessionId) {
        long lastInteractionTime = getLastInteractionTime(sessionId);
        long now = System.currentTimeMillis();

        return now - lastInteractionTime > (long) onlineLifespanSec * 1000;
    }
}
