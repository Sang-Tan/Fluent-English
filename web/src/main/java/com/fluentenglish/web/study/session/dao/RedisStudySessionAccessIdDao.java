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
        return stringTemplate.opsForValue().get(getAccessIdReferSessionIdKey(accessId));
    }

    @Override
    public void createAccessIdForSession(String sessionId, String accessId) {
        stringTemplate.opsForValue().set(getAccessIdReferSessionIdKey(accessId), sessionId);
        stringTemplate.opsForValue().set(getSessionIdReferAccessIdKey(sessionId), accessId);
    }

    @Override
    public void changeAccessIdBySessionId(String sessionId, String newAccessId) {
        String oldAccessId = stringTemplate.opsForValue().get(getSessionIdReferAccessIdKey(sessionId));
        stringTemplate.delete(getAccessIdReferSessionIdKey(oldAccessId));

        stringTemplate.opsForValue().set(getAccessIdReferSessionIdKey(newAccessId), sessionId);
        stringTemplate.opsForValue().set(getSessionIdReferAccessIdKey(sessionId), newAccessId);
    }

    @Override
    public String getAccessIdBySessionId(String sessionId) {
        return stringTemplate.opsForValue().get(getSessionIdReferAccessIdKey(sessionId));
    }

    @Override
    public void deleteSessionAccessId(String sessionId) {
        String accessId = stringTemplate.opsForValue().get(getSessionIdReferAccessIdKey(sessionId));
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
