package com.fluentenglish.web.study.session.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StudySessionUpdateInfo.class, name = "update"),
        @JsonSubTypes.Type(value = StudySessionSummary.class, name = "summary")
})
public abstract class StudySessionInfo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sessionId;
}
