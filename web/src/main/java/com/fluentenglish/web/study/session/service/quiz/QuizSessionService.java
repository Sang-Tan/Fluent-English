package com.fluentenglish.web.study.session.service.quiz;

import com.fluentenglish.web.study.session.dao.quiz.Quiz;
import com.fluentenglish.web.study.session.service.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.service.quiz.dto.resp.AnswerSubmissionResult;
import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.Set;

public interface QuizSessionService {
    /**
     * Create quizzes for session
     * @return the first quiz
     */
    Quiz initializeQuizzes(String sessionId, Set<Integer> wordIds);

    Optional<Quiz> getNextQuiz(String sessionId);

    AnswerSubmissionResult submitAnswer(String sessionId, AnswerSubmission answer);

    /**
     * User failed to provide answer
     */
    AnswerSubmissionResult handleFailedAnswerSubmission(String sessionId);

    int countRemainingQuizzes(String studySessionId);
}
