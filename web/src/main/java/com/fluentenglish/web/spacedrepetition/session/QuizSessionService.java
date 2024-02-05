package com.fluentenglish.web.spacedrepetition.session;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.spacedrepetition.quiz.QuizMemo;
import com.fluentenglish.web.spacedrepetition.quiz.QuizMemoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class QuizSessionService {
    private final SessionRepository sessionRepository;
    private final QuizMemoRepository quizMemoRepository;
    public QuizSessionService(SessionRepository sessionRepository, QuizMemoRepository quizMemoRepository) {
        this.quizMemoRepository = quizMemoRepository;
        this.sessionRepository = sessionRepository;
    }
    private Integer calculatePoints(Integer timeAnsweredMs, Integer maxTimeMs) {
        return (int) Math.round(10 - 10 * Math.pow(Float.valueOf(timeAnsweredMs) / maxTimeMs, 1.5f));
    }
    public void answerQuiz(AnswerQuizDto dto) {
        QuizMemo quizMemo = quizMemoRepository.findById(dto.getQuizId()).orElseThrow(() -> new NotFoundException("Quiz id: " + dto.getQuizId() + " not found"));
        quizMemo.setAnswered(true);
        quizMemo.setCorrect(dto.isCorrect());
        quizMemo.setTimeAnsweredMs(dto.getTimeAnsweredMs());
        quizMemo.setPoints(calculatePoints(dto.getTimeAnsweredMs(), quizMemo.getMaxTimeMs()));
        quizMemoRepository.save(quizMemo);
    }
}
