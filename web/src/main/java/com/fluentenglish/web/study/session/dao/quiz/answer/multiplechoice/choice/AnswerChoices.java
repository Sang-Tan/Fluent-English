package com.fluentenglish.web.study.session.dao.quiz.answer.multiplechoice.choice;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextAnswerChoices.class, name = ChoiceTypes.TEXT),
        @JsonSubTypes.Type(value = AudioAnswerChoices.class, name = ChoiceTypes.AUDIO)
})
public abstract class AnswerChoices<T> {
    protected List<T> data = new ArrayList<>();
}
