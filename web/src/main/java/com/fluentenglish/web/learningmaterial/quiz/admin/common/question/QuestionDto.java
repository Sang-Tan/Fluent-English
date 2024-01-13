package com.fluentenglish.web.learningmaterial.quiz.admin.common.question;

import com.fluentenglish.web.learningmaterial.quiz.admin.common.media.MediaDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDto {
    protected String content;
    protected MediaDto attachment;
}
