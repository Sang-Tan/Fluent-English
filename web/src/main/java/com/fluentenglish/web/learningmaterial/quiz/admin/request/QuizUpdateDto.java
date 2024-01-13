package com.fluentenglish.web.learningmaterial.quiz.admin.request;

import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.AnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.question.QuestionDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizUpdateDto {
    @NotNull
    private QuestionDto question;

    @NotNull
    private AnswerDto answer;
}
