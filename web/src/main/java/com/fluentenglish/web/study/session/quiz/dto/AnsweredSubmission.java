package com.fluentenglish.web.study.session.quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnsweredSubmission extends AnswerSubmission{
    private String answer;
    private Integer timeAnsweredSec;
}
