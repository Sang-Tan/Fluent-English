package com.fluentenglish.web.study.session.dao;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisStudySessionAccessIdDao implements StudySessionAccessIdDao {

    private final StringRedisTemplate stringTemplate;

    public RedisStudySessionAccessIdDao(StringRedisTemplate stringTemplate) {
        this.stringTemplate = stringTemplate;
    }

    @Override
    public String getSessionIdByAccessId(String accessId) {
        String sessionId = stringTemplate.opsForValue().get(getAccessIdReferSessionIdKey(accessId));
        if (sessionId == null) {
            throw new RuntimeException("No session found for given accessId");
        }

        return sessionId;
    }

    @Override
    public void createAccessIdForSession(String sessionId, String accessId) {
        stringTemplate.opsForValue().set(getAccessIdReferSessionIdKey(accessId), sessionId);
        stringTemplate.opsForValue().set(getSessionIdReferAccessIdKey(sessionId), accessId);
    }

    @Override
    public void changeAccessIdBySessionId(String sessionId, String newAccessId) {
        String oldAccessId = stringTemplate.opsForValue().get(getSessionIdReferAccessIdKey(sessionId));
        if (oldAccessId == null) {
            throw new RuntimeException("No accessId found for given sessionId");
        }
        stringTemplate.delete(getAccessIdReferSessionIdKey(oldAccessId));

        stringTemplate.opsForValue().set(getAccessIdReferSessionIdKey(newAccessId), sessionId);
        stringTemplate.opsForValue().set(getSessionIdReferAccessIdKey(sessionId), newAccessId);
    }

    @Override
    public String getAccessIdBySessionId(String sessionId) {
        String accessId = stringTemplate.opsForValue().get(getSessionIdReferAccessIdKey(sessionId));
        if (accessId == null) {
            throw new RuntimeException("No accessId found for given sessionId");
        }

        return accessId;
    }

    @Override
    public void deleteSessionAccessId(String sessionId) {
        String accessId = stringTemplate.opsForValue().get(getSessionIdReferAccessIdKey(sessionId));
        if (accessId == null) {
            throw new RuntimeException("No accessId found for given sessionId");
        }
        
        stringTemplate.delete(getAccessIdReferSessionIdKey(accessId));
        stringTemplate.delete(getSessionIdReferAccessIdKey(sessionId));
    }

    private String getAccessIdReferSessionIdKey(String accessId) {
        return "studySessionAccessId:" + accessId + ":sessionId";
    }

    private String getSessionIdReferAccessIdKey(String sessionId) {
        return "studySession:" + sessionId + ":accessId";
    }
}
