package com.fluentenglish.web.study.session.dao.lastinteraction;

public interface SessionLastInteraction {

    long getLastInteractionTime();

    void setLastInteractionTime(long lastInteractionTimeMs);
}
