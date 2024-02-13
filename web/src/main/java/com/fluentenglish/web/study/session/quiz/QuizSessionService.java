package com.fluentenglish.web.study.session.quiz;

import com.fluentenglish.web.study.session.quiz.dto.AnswerSubmission;

import java.util.Optional;
import java.util.Set;

public interface QuizSessionService {
    /**
     * Create quizzes for session
     * @return the first quiz
     */
    Quiz initializeQuizzes(String sessionId, Set<Integer> wordIds);

    Optional<Quiz> getNextQuiz(String sessionId);

    /**
     * @return score of answered quiz
     */
    Integer submitAnswer(String sessionId, AnswerSubmission answer);
}
