package com.fluentenglish.web.study.session.service.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fluentenglish.web.study.session.quiz.dto.resp.AnswerSubmissionResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StudySessionUpdateDto.class, name = "update"),
        @JsonSubTypes.Type(value = StudySessionSummaryDto.class, name = "summary")
})
public abstract class StudySessionSubmissionDto {
    private AnswerSubmissionResult answerSubmissionResult;
}
