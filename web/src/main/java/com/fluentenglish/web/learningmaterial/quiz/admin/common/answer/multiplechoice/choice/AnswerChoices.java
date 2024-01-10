package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.choice;

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
        @JsonSubTypes.Type(value = TextChoices.class, name = ChoiceConstants.TYPE_TEXT),
        @JsonSubTypes.Type(value = AudioChoices.class, name = ChoiceConstants.TYPE_AUDIO)
})
public abstract class AnswerChoices<T> {
    protected List<T> data = new ArrayList<>();
}
