package com.fluentenglish.web.study.session;

public abstract class RedisStudySessionObject {
    private StudySessionMetadata metadata;

    public abstract void delete();

    public final void setSessionMetadata(StudySessionMetadata metadata) {
        if (this.metadata != null) {
            throw new IllegalStateException("Session's metadata is already set and cannot be changed");
        }

        this.metadata = metadata;
    }

    protected final StudySessionMetadata getSessionMetadata() {
        if (metadata == null) {
            throw new IllegalStateException("Session's metadata is not set");
        }

        return metadata;
    }
}
