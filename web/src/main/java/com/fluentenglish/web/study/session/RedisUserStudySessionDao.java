package com.fluentenglish.web.study.session;

import com.fluentenglish.web.study.session.exception.StudySessionExistsException;
import com.fluentenglish.web.study.session.exception.StudySessionNotExistException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RedisUserStudySessionDao implements UserStudySessionDao {
    private final BeanFactory beanFactory;

    private final RedisTemplate<String, Object> redisTemplate;

    private final ValueOperations<String, Integer> integerOperations;

    private final ValueOperations<String, String> stringOperations;

    public RedisUserStudySessionDao(BeanFactory beanFactory,
                                    RedisTemplate<String, Object> redisTemplate,
                                    ValueOperations<String, Integer> integerOperations,
                                    ValueOperations<String, String> stringOperations) {
        this.beanFactory = beanFactory;
        this.redisTemplate = redisTemplate;
        this.integerOperations = integerOperations;
        this.stringOperations = stringOperations;
    }

    @Override
    public StudySession createSession(int userId) {
        if (studySessionOfUserExists(userId)) {
            throw new StudySessionExistsException();
        }
        String sessionId = generateRandomSessionId();
        stringOperations.set(getUserReferSessionKey(userId), sessionId);
        integerOperations.set(getSessionReferUserKey(sessionId), userId);

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionMetadata(sessionId, userId));

        return session;
    }

    @Override
    public StudySession getSessionByUserId(int userId) {
        String sessionId = getSessionIdByUserId(userId)
                .orElseThrow(StudySessionNotExistException::new);

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionMetadata(sessionId, userId));

        return session;
    }

    @Override
    public StudySession getSessionById(String sessionId) {
        Integer userId = integerOperations.get(getSessionReferUserKey(sessionId));
        if (userId == null) {
            throw new StudySessionNotExistException();
        }

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionMetadata(sessionId, userId));

        return session;
    }

    @Override
    public boolean studySessionOfUserExists(int userId) {
        return stringOperations.get(getUserReferSessionKey(userId)) != null;
    }

    @Override
    public boolean studySessionExists(String sessionId) {
        return integerOperations.get(getSessionReferUserKey(sessionId)) != null;
    }

    @Override
    public void deleteStudySession(String sessionId) {
        Integer userId = integerOperations.get(getSessionReferUserKey(sessionId));
        if (userId == null) {
            return;
        }

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionMetadata(sessionId, userId));
        session.delete();

        redisTemplate.delete(getSessionReferUserKey(sessionId));
        redisTemplate.delete(getUserReferSessionKey(userId));
    }

    private String getUserReferSessionKey(int userId) {
        return String.format("user:%d:studySessionId", userId);
    }

    private String getSessionReferUserKey(String sessionId) {
        return String.format("studySession:%s:userId", sessionId);
    }

    private Optional<String> getSessionIdByUserId(int userId) {
        return Optional.ofNullable(stringOperations.get(getUserReferSessionKey(userId)));
    }

    private String generateRandomSessionId() {
        return UUID.randomUUID().toString();
    }
}
