package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.response.QuizDto;

import java.util.List;

public interface QuizService {
    List<QuizDto> getQuizzesByExerciseId(int exerciseId);
    QuizDto getQuizById(int id);
    int createQuiz(QuizCreateDto quizCreateDto);
    void updateQuiz(int id, QuizUpdateDto quizUpdateDto);
    void deleteQuiz(int id);
}
