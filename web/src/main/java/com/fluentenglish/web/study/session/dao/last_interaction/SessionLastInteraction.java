package com.fluentenglish.web.study.session.dao.last_interaction;

public interface SessionLastInteraction {

    long getLastInteractionTime();

    void setLastInteractionTime(long lastInteractionTimeMs);
}
