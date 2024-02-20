package com.fluentenglish.web.study.session.dao.quiz.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fluentenglish.web.study.session.dao.quiz.answer.input.InputAnswer;
import com.fluentenglish.web.study.session.dao.quiz.answer.multiplechoice.MultipleChoiceAnswer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceAnswer.class, name = AnswerTypes.MULTIPLE_CHOICE),
        @JsonSubTypes.Type(value = InputAnswer.class, name = AnswerTypes.INPUT)
})
public class Answer {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String explanation;
}
