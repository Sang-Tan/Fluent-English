package com.fluentenglish.web.study.session.dao;

import com.fluentenglish.web.study.session.dao.exception.StudySessionExistsException;
import com.fluentenglish.web.study.session.dao.exception.StudySessionNotExistException;
import com.fluentenglish.web.study.session.dao.meta.StudySessionManageMetadata;
import com.fluentenglish.web.study.session.dao.score.RedisMetadataDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Log4j2
@Repository
public class RedisStudySessionDao implements StudySessionDao {
    private final BeanFactory beanFactory;

    private final HashMapper<RedisMetadataDto, String, String> hashMapper =
            new BeanUtilsHashMapper<>(RedisMetadataDto.class);

    private final RedisTemplate<String, String> stringTemplate;

    private final ValueOperations<String, String> stringOperations;

    private final HashOperations<String, String, String> hashOperations;

    public RedisStudySessionDao(BeanFactory beanFactory,
                                StringRedisTemplate stringTemplate) {
        this.beanFactory = beanFactory;
        this.stringOperations = stringTemplate.opsForValue();
        this.hashOperations = stringTemplate.opsForHash();
        this.stringTemplate = stringTemplate;
    }

    @Override
    public StudySession createSession(int userId) {
        if (studySessionOfUserExists(userId)) {
            throw new StudySessionExistsException();
        }

        String sessionId = generateRandomSessionId();
        hashOperations.putAll(getSessionMetadataKey(sessionId),
                hashMapper.toHash(new RedisMetadataDto(userId)));
        stringOperations.set(getUserReferSessionKey(userId), sessionId);

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionManageMetadata(sessionId, userId));

        return session;
    }

    @Override
    public StudySession getSessionById(String sessionId) {
        RedisMetadataDto metadata = getMetadataBySessionId(sessionId);

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionManageMetadata(sessionId, metadata.getUserId()));

        return session;
    }

    @Override
    public String getSessionIdByUserId(int userId) {
        String sessionId = stringOperations.get(getUserReferSessionKey(userId));

        if (sessionId == null) {
            throw new StudySessionNotExistException();
        }
        return sessionId;
    }

    @Override
    public boolean studySessionOfUserExists(int userId) {
        return Boolean.TRUE.equals(stringTemplate.hasKey(getUserReferSessionKey(userId)));
    }

    @Override
    public boolean studySessionExists(String sessionId) {
        return Boolean.TRUE.equals(stringTemplate.hasKey(getSessionMetadataKey(sessionId)));
    }

    @Override
    public void deleteStudySession(String sessionId) {
        if (!studySessionExists(sessionId)) {
            log.warn("Trying to delete non-existing session");
            return;
        }
        int userId = getMetadataBySessionId(sessionId).getUserId();

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionManageMetadata(
                sessionId, userId));
        session.delete();

        stringTemplate.delete(getSessionMetadataKey(sessionId));
        stringTemplate.delete(getUserReferSessionKey(userId));
    }

    private String getUserReferSessionKey(int userId) {
        return String.format("user:%d:studySessionId", userId);
    }

    private String getSessionMetadataKey(String sessionId) {
        return String.format("studySession:%s:metadata", sessionId);
    }

    private RedisMetadataDto getMetadataBySessionId(String sessionId) {
        return hashMapper.fromHash(hashOperations.entries(getSessionMetadataKey(sessionId)));
    }

    private String generateRandomSessionId() {
        return UUID.randomUUID().toString();
    }
}
