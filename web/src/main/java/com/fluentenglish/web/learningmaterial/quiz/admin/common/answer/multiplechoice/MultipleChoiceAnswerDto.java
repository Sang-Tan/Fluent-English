package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice;

import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.AnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.choice.AnswerChoices;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleChoiceAnswerDto extends AnswerDto {
    protected AnswerChoices<?> choices;
    protected int correctChoice;
}
