package com.fluentenglish.web.study.session.dao.quiz.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fluentenglish.web.common.fileresource.FileResourceDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {
    private String content;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FileResourceDto attachment;
}
