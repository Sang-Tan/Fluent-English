package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;

public interface QuizMediaService {
    void saveMediaOnCreated(QuizCreateDto quizCreateDto);
    void saveMediaOnUpdated(QuizUpdateDto quizUpdateDto, Quiz persistedQuiz);
    void deleteMediaOnDeleted(Quiz quiz);
}
