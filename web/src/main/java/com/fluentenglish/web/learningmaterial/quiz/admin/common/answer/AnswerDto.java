package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.input.InputAnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.MultipleChoiceAnswerDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceAnswerDto.class,
                name = AnswerConstants.TYPE_MULTIPLE_CHOICE),
        @JsonSubTypes.Type(value = InputAnswerDto.class,
                name = AnswerConstants.TYPE_INPUT)
})
public abstract class AnswerDto {
    protected String explanation;
}
