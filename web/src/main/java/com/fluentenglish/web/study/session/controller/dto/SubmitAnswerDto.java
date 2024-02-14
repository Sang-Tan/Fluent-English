package com.fluentenglish.web.study.session.controller.dto;

import com.fluentenglish.web.study.session.quiz.dto.AnswerSubmission;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitAnswerDto extends ExistStudySessionRequestDto{
    @NotNull
    private AnswerSubmission answer;
}
