package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.input;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.AnswerDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize
public class InputAnswerDto extends AnswerDto {
    protected String answer;
}