package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.response.QuizDto;

import java.util.List;

public interface QuizService {
    List<QuizDto> getQuizzesByExerciseId(Integer exerciseId);
    QuizDto getQuizById(Integer id);
    Integer createQuiz(QuizCreateDto quizCreateDto);
    void updateQuiz(Integer id, QuizUpdateDto quizUpdateDto);
    void deleteQuiz(Integer id);
}
