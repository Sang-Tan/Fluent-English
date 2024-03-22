package com.fluentenglish.web.study.session.dao;

public interface StudySessionAccessIdDao {
    
    String getSessionIdByAccessId(String accessId);

    void createAccessIdForSession(String sessionId, String accessId);

    void changeAccessIdBySessionId(String sessionId, String newAccessId);

    String getAccessIdBySessionId(String sessionId);

    void deleteSessionAccessId(String sessionId);
}
