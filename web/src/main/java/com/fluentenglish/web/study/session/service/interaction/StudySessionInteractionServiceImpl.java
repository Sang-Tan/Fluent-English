package com.fluentenglish.web.study.session.service.interaction;

import com.fluentenglish.web.study.session.dao.RedisUserStudySessionDao;
import com.fluentenglish.web.study.session.dao.StudySession;
import com.fluentenglish.web.study.session.dao.last_interaction.SessionLastInteraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StudySessionInteractionServiceImpl implements StudySessionInteractionService {
    private final RedisUserStudySessionDao redisUserStudySessionDao;

    private final int onlineLifespanSec;

    public StudySessionInteractionServiceImpl(@Value("${study-session.online-lifespan}") int onlineLifespanSec,
                                              RedisUserStudySessionDao redisUserStudySessionDao) {
        this.onlineLifespanSec = onlineLifespanSec;
        this.redisUserStudySessionDao = redisUserStudySessionDao;
    }

    @Override
    public void setLastInteractionTime(String sessionId, long lastInteractionTimeMs) {
        StudySession studySession = redisUserStudySessionDao.getSessionById(sessionId);
        SessionLastInteraction sessionLastInteraction = studySession.getLastInteraction();

        sessionLastInteraction.setLastInteractionTime(lastInteractionTimeMs);
    }

    @Override
    public long getLastInteractionTime(String sessionId) {
        StudySession studySession = redisUserStudySessionDao.getSessionById(sessionId);
        SessionLastInteraction sessionLastInteraction = studySession.getLastInteraction();

        return sessionLastInteraction.getLastInteractionTime();
    }

    @Override
    public boolean isSessionActive(String sessionId) {
        long lastInteractionTime = getLastInteractionTime(sessionId);
        long now = System.currentTimeMillis();

        return now - lastInteractionTime < (long) onlineLifespanSec * 1000;
    }
}
