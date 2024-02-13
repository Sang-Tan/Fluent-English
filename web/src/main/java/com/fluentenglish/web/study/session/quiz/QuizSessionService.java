package com.fluentenglish.web.study.session.quiz;

import com.fluentenglish.web.study.session.quiz.answer.Answer;
import com.fluentenglish.web.study.session.quiz.dto.AnswerSubmission;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuizSessionService {
    public SessionQuizzesQueue createQuizzesQueue(String sessionId, Set<Integer> wordIds);

    public Optional<Quiz> getNextQuiz(String sessionId);

    /**
     * @return score of answered quiz
     */
    public Integer submitAnswer(String sessionId, AnswerSubmission answer);
}
