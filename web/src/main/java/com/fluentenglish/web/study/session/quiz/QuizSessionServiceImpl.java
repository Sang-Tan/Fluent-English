package com.fluentenglish.web.study.session.quiz;

import com.fluentenglish.web.study.session.RedisUserStudySessionDao;
import com.fluentenglish.web.study.session.StudySession;
import com.fluentenglish.web.study.session.quiz.answer.Answer;
import com.fluentenglish.web.study.session.quiz.answer.input.InputAnswer;
import com.fluentenglish.web.study.session.quiz.answer.multiplechoice.MultipleChoiceAnswer;
import com.fluentenglish.web.study.session.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.quiz.dto.AnsweredSubmission;
import com.fluentenglish.web.study.session.quiz.dto.NotAnswerSubmission;
import com.fluentenglish.web.study.session.quiz.dto.resp.AnswerSubmissionResult;
import com.fluentenglish.web.study.session.quiz.dto.resp.CorrectAnswerSubmissionResult;
import com.fluentenglish.web.study.session.quiz.dto.resp.IncorrectAnswerSubmissionResult;
import com.fluentenglish.web.study.session.quiz.generator.QuizGenerateService;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizSessionServiceImpl implements QuizSessionService {
    private final QuizGenerateService quizGenerateService;
    private final RedisUserStudySessionDao redisUserStudySessionDao;

    public QuizSessionServiceImpl(QuizGenerateService quizGenerateService,
                                  RedisUserStudySessionDao redisUserStudySessionDao) {
        this.quizGenerateService = quizGenerateService;
        this.redisUserStudySessionDao = redisUserStudySessionDao;
    }

    @Override
    public Quiz initializeQuizzes(String sessionId, Set<Integer> wordIds) {
        return createRedisQuizzesQueue(sessionId, wordIds).peek();
    }

    @Override
    public Optional<Quiz> getNextQuiz(String sessionId) {
        return getNextQuizFromRedisQueue(sessionId);
    }

    @Override
    public AnswerSubmissionResult submitAnswer(String sessionId, AnswerSubmission answer) {
        return submitAnswerToRedisQueue(sessionId, answer);
    }

    @Override
    public int countRemainingQuizzes(String studySessionId) {
        return redisUserStudySessionDao.getSessionById(studySessionId).getQuizzesQueue().quizzesCount();
    }

    private Optional<Quiz> getNextQuizFromRedisQueue(String sessionId) {
        StudySession studySession = redisUserStudySessionDao.getSessionById(sessionId);
        SessionQuizzesQueue quizzesQueue = studySession.getQuizzesQueue();

        return Optional.ofNullable(quizzesQueue.peek());
    }

    private AnswerSubmissionResult submitAnswerToRedisQueue(String sessionId, AnswerSubmission answer) {
        StudySession studySession = redisUserStudySessionDao.getSessionById(sessionId);
        SessionQuizzesQueue quizzesQueue = studySession.getQuizzesQueue();
        Quiz quizAnswered = quizzesQueue.poll();

        String correctAnswer;

        if (answer instanceof NotAnswerSubmission) {
            correctAnswer = getCorrectAnswer(quizAnswered);
            quizzesQueue.add(quizAnswered);

            return new IncorrectAnswerSubmissionResult(correctAnswer);
        } else if (answer instanceof AnsweredSubmission answeredSubmission) {
            correctAnswer = getAnswerIfIncorrect(answeredSubmission.getAnswer(), quizAnswered);
            if (correctAnswer != null) {
                quizzesQueue.add(quizAnswered);
                return new IncorrectAnswerSubmissionResult(correctAnswer);
            }

            Integer score = calculateQuizScore(answeredSubmission.getTimeAnsweredSec(), quizAnswered.getMaxTimeSec());
            return new CorrectAnswerSubmissionResult(score);
        }

        throw new IllegalArgumentException("Answer submission type not supported");
    }

    private SessionQuizzesQueue createRedisQuizzesQueue(String sessionId, Set<Integer> wordIds) {
        StudySession studySession = redisUserStudySessionDao.getSessionById(sessionId);
        SessionQuizzesQueue sessionQuizzesQueue = studySession.getQuizzesQueue();
        List<Quiz> quizzes = new ArrayList<>();
        for (Integer wordId : wordIds) {
            quizzes.add(quizGenerateService.genListenQuesEngInputAnsQuiz(wordId));
            quizzes.add(quizGenerateService.genVietTextQuesEngChoicesAnsQuiz(wordId, wordIds));
        }
        Collections.shuffle(quizzes);
        sessionQuizzesQueue.add(quizzes);

        return sessionQuizzesQueue;
    }

    @Nullable
    private String getAnswerIfIncorrect(String submittedAnswer, Quiz quizAnswered) {
        String correctAnswer = getCorrectAnswer(quizAnswered);

        if (quizAnswered.getAnswer() instanceof InputAnswer) {
            return isInputAnswerCorrect(submittedAnswer, correctAnswer) ? null : correctAnswer;
        } else if (quizAnswered.getAnswer() instanceof MultipleChoiceAnswer) {
            return isMultipleChoiceAnswerCorrect(submittedAnswer, correctAnswer) ? null : correctAnswer;
        }

        throw new IllegalArgumentException("Answer type not supported");
    }

    private String getCorrectAnswer(Quiz quizAnswered) {
        Answer answer = quizAnswered.getAnswer();
        if (answer instanceof InputAnswer) {
            return ((InputAnswer) quizAnswered.getAnswer()).getAnswer();
        } else if (answer instanceof MultipleChoiceAnswer) {
            return String.valueOf(((MultipleChoiceAnswer) quizAnswered.getAnswer()).getCorrectChoice());
        }

        throw new IllegalArgumentException("Answer type not supported");
    }

    private boolean isInputAnswerCorrect(String inputAnswer, String correctAnswer) {
        return correctAnswer.equalsIgnoreCase(inputAnswer.trim());
    }

    private boolean isMultipleChoiceAnswerCorrect(String choiceIndex, String correctIndex) {
        return correctIndex.equals(choiceIndex);
    }


    private Integer calculateQuizScore(Integer timeAnsweredSec, Integer maxTimeSec) {
        if (timeAnsweredSec > maxTimeSec) {
            throw new IllegalArgumentException("Answer submission time exceeded max time");
        }

        double timeRatio = (double) timeAnsweredSec / maxTimeSec;
        return Math.toIntExact(Math.round(10 - Math.pow(timeRatio, 1.5) * 10));
    }
}
