package com.fluentenglish.web.study.session.dao;

import com.fluentenglish.web.study.session.dao.meta.StudySessionMetadata;

public abstract class RedisStudySessionObject<T extends StudySessionMetadata> {
    private T metadata;

    public abstract void delete();

    public final void setSessionMetadata(T metadata) {
        if (this.metadata != null) {
            throw new IllegalStateException("Session's metadata is already set and cannot be changed");
        }

        this.metadata = metadata;
    }

    protected final T getSessionMetadata() {
        if (metadata == null) {
            throw new IllegalStateException("Session's metadata is not set");
        }

        return metadata;
    }
}
