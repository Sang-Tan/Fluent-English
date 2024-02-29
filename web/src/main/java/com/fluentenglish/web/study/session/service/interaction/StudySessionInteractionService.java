package com.fluentenglish.web.study.session.service.interaction;

public interface StudySessionInteractionService {
    void setLastInteractionTime(String sessionId, long lastInteractionTimeMs);

    long getLastInteractionTime(String sessionId);

    /**
     * Check if the session is currently used by the user
     */
    boolean isSessionActive(String sessionId);
}
