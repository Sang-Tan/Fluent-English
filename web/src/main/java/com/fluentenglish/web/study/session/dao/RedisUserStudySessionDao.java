package com.fluentenglish.web.study.session.dao;

import com.fluentenglish.web.study.session.dao.exception.StudySessionExistsException;
import com.fluentenglish.web.study.session.dao.exception.StudySessionNotExistException;
import com.fluentenglish.web.study.session.dao.meta.StudySessionManageMetadata;
import com.fluentenglish.web.study.session.dao.score.RedisMetadataDto;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RedisUserStudySessionDao implements UserStudySessionDao {
    private final BeanFactory beanFactory;

    private final HashMapper<Object, String, Object> hashMapper = new Jackson2HashMapper(false);

    private final RedisTemplate<String, String> stringTemplate;

    private final ValueOperations<String, String> stringOperations;

    private final HashOperations<String, String, Object> hashOperations;

    public RedisUserStudySessionDao(BeanFactory beanFactory,
                                    RedisTemplate<String, String> stringTemplate) {
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
        String sessionAccessId = generateRandomAccessSessionId();

        hashOperations.putAll(getSessionReferMetadataKey(sessionId), hashMapper.toHash(new RedisMetadataDto(userId, sessionAccessId)));
        stringOperations.set(getUserReferSessionKey(userId), sessionId);
        stringOperations.set(getAccessReferSessionKey(sessionAccessId), sessionAccessId);

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionManageMetadata(sessionAccessId, sessionId, userId));

        return session;
    }

    @Override
    public StudySession getSessionByUserId(int userId) {
        String sessionId = getSessionIdByUserId(userId);
        RedisMetadataDto metadata = getMetadataBySessionId(sessionId);

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionManageMetadata(
                metadata.getSessionAccessId(), sessionId, metadata.getUserId()));

        return session;
    }

    @Override
    public String renewSessionAccessId(String sessionId) {
        String newAccessSessionId = generateRandomAccessSessionId();
        return renewSessionAccessId(sessionId, newAccessSessionId);
    }

    @Override
    public String renewSessionAccessId(String sessionId, String newAccessSessionId) {
        RedisMetadataDto metadata = getMetadataBySessionId(sessionId);

        stringTemplate.delete(getSessionReferMetadataKey(metadata.getSessionAccessId()));
        stringTemplate.delete(getAccessReferSessionKey(metadata.getSessionAccessId()));

        hashOperations.putAll(getSessionReferMetadataKey(sessionId), hashMapper.toHash(
                new RedisMetadataDto(metadata.getUserId(), metadata.getSessionAccessId())));
        stringOperations.set(getAccessReferSessionKey(newAccessSessionId), sessionId);

        return newAccessSessionId;
    }

    @Override
    public StudySession getSessionById(String sessionId) {
        RedisMetadataDto metadata = getMetadataBySessionId(sessionId);

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionManageMetadata(
                metadata.getSessionAccessId(), sessionId, metadata.getUserId()));

        return session;
    }

    @Override
    public StudySession getSessionByAccessId(String accessSessionId) {
        String sessionId = getSessionIdByAccessSessionId(accessSessionId);
        RedisMetadataDto metadata = getMetadataBySessionId(sessionId);

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionManageMetadata(
                metadata.getSessionAccessId(), sessionId, metadata.getUserId()));

        return session;
    }

    @Override
    public boolean studySessionOfUserExists(int userId) {
        return stringOperations.get(getUserReferSessionKey(userId)) != null;
    }

    @Override
    public boolean studySessionExists(String sessionId) {
        return stringOperations.get(getSessionReferMetadataKey(sessionId)) != null;
    }

    @Override
    public void deleteStudySession(String sessionId) {
        String userIdStr = stringOperations.get(getSessionReferMetadataKey(sessionId));
        String accessSessionId = generateRandomAccessSessionId();
        if (userIdStr == null) {
            return;
        }
        int userId = Integer.parseInt(userIdStr);

        RedisStudySession session = beanFactory.getBean(RedisStudySession.class);
        session.setSessionMetadata(new StudySessionManageMetadata(accessSessionId, sessionId, userId));
        session.delete();

        stringTemplate.delete(getSessionReferMetadataKey(sessionId));
        stringTemplate.delete(getUserReferSessionKey(userId));
        stringTemplate.delete(getAccessReferSessionKey(accessSessionId));
    }

    private String getUserReferSessionKey(int userId) {
        return String.format("user:%d:studySessionId", userId);
    }

    private String getSessionReferMetadataKey(String sessionId) {
        return String.format("studySession:%s:metadata", sessionId);
    }

    private String getAccessReferSessionKey(String sessionAccessId) {
        return String.format("sessionAccessId:%s:studySessionId", sessionAccessId);
    }

    private String getSessionIdByUserId(int userId) {
        String sessionId = stringOperations.get(getUserReferSessionKey(userId));

        if (sessionId == null) {
            throw new StudySessionNotExistException();
        }
        return sessionId;
    }

    private String getSessionIdByAccessSessionId(String sessionAccessId) {
        String sessionId = stringOperations.get(getAccessReferSessionKey(sessionAccessId));

        if (sessionId == null) {
            throw new StudySessionNotExistException();
        }
        return sessionId;
    }

    private RedisMetadataDto getMetadataBySessionId(String sessionId) {
        return (RedisMetadataDto)
                hashMapper.fromHash(hashOperations.entries(getSessionReferMetadataKey(sessionId)));
    }

    private String generateRandomSessionId() {
        return UUID.randomUUID().toString();
    }

    private String generateRandomAccessSessionId() {
        return UUID.randomUUID().toString();
    }
}
