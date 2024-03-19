package com.fluentenglish.web.study.session.context;

import com.fluentenglish.web.study.session.dao.DataHolderStudySession;

import java.util.HashMap;
import java.util.Map;

public class DataHolderStudySessionContainer {
    private final Map<String, DataHolderStudySession> sessionsBySessionId = new HashMap<>();

    public DataHolderStudySession getSessionById(String sessionId) {
        return sessionsBySessionId.get(sessionId);
    }

    public Map<String, DataHolderStudySession> getSessions() {
        return sessionsBySessionId;
    }

    public void putSession(String sessionId, DataHolderStudySession session) {
        sessionsBySessionId.put(sessionId, session);
    }

    public boolean containsKey(String sessionId) {
        return sessionsBySessionId.containsKey(sessionId);
    }

    public void removeSession(String sessionId) {
        sessionsBySessionId.remove(sessionId);
    }
}
