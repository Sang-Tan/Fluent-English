package com.fluentenglish.web.study.session.dao.quiz;

import com.fluentenglish.web.study.session.dao.quiz.answer.Answer;
import com.fluentenglish.web.study.session.dao.quiz.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    private int wordId;

    private Question question;

    private Answer answer;

    private Integer maxTimeSec;
}
