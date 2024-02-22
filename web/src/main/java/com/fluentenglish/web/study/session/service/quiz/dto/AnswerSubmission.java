package com.fluentenglish.web.study.session.service.quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerSubmission {
    private String answer;
    private Integer timeAnsweredSec;
}
