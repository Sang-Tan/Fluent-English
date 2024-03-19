package com.fluentenglish.web.study.session.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentenglish.web.study.session.context.DataHolderStudySessionContainer;
import com.fluentenglish.web.study.session.context.StudySessionContext;
import com.fluentenglish.web.study.session.dao.exception.StudySessionNotExistException;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Primary
@Repository
public class RedisStudySessionDao implements StudySessionDao {
    private final ObjectMapper objectMapper;

    private final ValueOperations<String, String> stringOperations;

    private final StudySessionContext studySessionContext;

    public RedisStudySessionDao(ObjectMapper objectMapper,
                                StringRedisTemplate stringRedisTemplate, StudySessionContext studySessionContext) {
        this.objectMapper = objectMapper;
        this.stringOperations = stringRedisTemplate.opsForValue();
        this.studySessionContext = studySessionContext;
    }

    @Override
    public StudySession createSession(int userId) {
        DataHolderStudySession dataHolderStudySession = new DataHolderStudySession();
        String sessionId = generateSessionId();

        dataHolderStudySession.setUserId(userId);
        dataHolderStudySession.setId(sessionId);

        DataHolderStudySessionContainer container = studySessionContext.getContainer();
        container.putSession(sessionId, dataHolderStudySession);

        return dataHolderStudySession;
    }

    @Override
    public StudySession getSessionById(String sessionId) {
        DataHolderStudySessionContainer container = studySessionContext.getContainer();
        if (container.containsKey(sessionId)) {
            return container.getSessionById(sessionId);
        }

        String sessionJson = stringOperations.get(getSessionKey(sessionId));
        if (sessionJson == null) {
            throw new StudySessionNotExistException();
        }

        DataHolderStudySession session = deserializeSession(sessionJson);
        container.putSession(sessionId, session);

        return session;
    }

    @Override
    public String getSessionIdByUserId(int userId) {
        String sessionId = stringOperations.get(getUserReferSessionIdKey(userId));
        if (sessionId == null) {
            throw new StudySessionNotExistException();
        }
        return sessionId;
    }

    @Override
    public boolean studySessionOfUserExists(int userId) {
        return stringOperations.get(getUserReferSessionIdKey(userId)) != null;
    }

    @Override
    public boolean studySessionExists(String sessionId) {
        return stringOperations.get(sessionId) != null;
    }

    @Override
    public void deleteStudySession(String sessionId) {
        String sessionKey = getSessionKey(sessionId);
        stringOperations.getOperations().delete(sessionKey);
        stringOperations.getOperations().delete(getUserReferSessionIdKey(getSessionById(sessionId).getUserId()));

        DataHolderStudySessionContainer container = studySessionContext.getContainer();
        container.removeSession(sessionId);
    }

    public void save() {
        DataHolderStudySessionContainer container = studySessionContext.getContainer();
        Map<String, DataHolderStudySession> sessions = container.getSessions();

        sessions.forEach((sessionId, session) -> {
            stringOperations.set(getSessionKey(sessionId), serializeSession(session));
            stringOperations.set(getUserReferSessionIdKey(session.getUserId()), session.getId());
        });
    }

    private String serializeSession(DataHolderStudySession studySession) {
        try {
            return objectMapper.writeValueAsString(studySession);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DataHolderStudySession deserializeSession(String sessionJson) {
        try {
            return objectMapper.readValue(sessionJson, DataHolderStudySession.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    private String getUserReferSessionIdKey(int userId) {
        return "user:" + userId + ":studySessionId";
    }

    private String getSessionKey(String sessionId) {
        return "studySession:" + sessionId;
    }
}
