package com.fluentenglish.web.study.session.quiz.answer.multiplechoice;

import com.fluentenglish.web.study.session.quiz.answer.Answer;
import com.fluentenglish.web.study.session.quiz.answer.multiplechoice.choice.AnswerChoices;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleChoiceAnswer extends Answer {
    private AnswerChoices<?> choices;

    private int correctChoice;
}
