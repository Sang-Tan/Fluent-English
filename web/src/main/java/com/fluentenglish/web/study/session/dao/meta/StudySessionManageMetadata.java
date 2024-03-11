package com.fluentenglish.web.study.session.dao.meta;

public record StudySessionManageMetadata(String sessionId, int userId)
        implements StudySessionMetadata {
}
