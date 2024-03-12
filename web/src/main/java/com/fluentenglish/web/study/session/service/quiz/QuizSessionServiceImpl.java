package com.fluentenglish.web.study.session.service.quiz;

import com.fluentenglish.web.study.session.dao.StudySession;
import com.fluentenglish.web.study.session.dao.StudySessionDao;
import com.fluentenglish.web.study.session.dao.quiz.Quiz;
import com.fluentenglish.web.study.session.dao.quiz.SessionQuizzesQueue;
import com.fluentenglish.web.study.session.dao.quiz.answer.Answer;
import com.fluentenglish.web.study.session.dao.quiz.answer.input.InputAnswer;
import com.fluentenglish.web.study.session.dao.quiz.answer.multiplechoice.MultipleChoiceAnswer;
import com.fluentenglish.web.study.session.service.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.service.quiz.dto.resp.AnswerSubmissionFailed;
import com.fluentenglish.web.study.session.service.quiz.dto.resp.AnswerSubmissionResult;
import com.fluentenglish.web.study.session.service.quiz.dto.resp.CorrectAnswerSubmissionResult;
import com.fluentenglish.web.study.session.service.quiz.exception.InvalidWordsCountException;
import com.fluentenglish.web.study.session.service.quiz.generator.QuizGenerateService;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizSessionServiceImpl implements QuizSessionService {
    private static final int MIN_WORDS_COUNT = 4;

    private static final int MAX_WORDS_COUNT = 10;

    private final QuizGenerateService quizGenerateService;
    private final StudySessionDao studySessionDao;

    public QuizSessionServiceImpl(QuizGenerateService quizGenerateService,
                                  StudySessionDao studySessionDao) {
        this.quizGenerateService = quizGenerateService;
        this.studySessionDao = studySessionDao;
    }

    @Override
    public Quiz initializeQuizzes(String sessionId, Set<Integer> wordIds) {
        if (wordIds.size() < MIN_WORDS_COUNT) {
            throw new InvalidWordsCountException("Required at least " + MIN_WORDS_COUNT + " words");
        } else if (wordIds.size() > MAX_WORDS_COUNT) {
            throw new InvalidWordsCountException("Required at most " + MAX_WORDS_COUNT + " words");
        }

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
    public AnswerSubmissionResult handleFailedAnswerSubmission(String sessionId) {
        return handleFailedAnswerOfRedisQueue(sessionId);
    }

    @Override
    public int countRemainingQuizzes(String studySessionId) {
        return studySessionDao.getSessionById(studySessionId).getQuizzesQueue().quizzesCount();
    }

    private Optional<Quiz> getNextQuizFromRedisQueue(String sessionId) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
        SessionQuizzesQueue quizzesQueue = studySession.getQuizzesQueue();

        return Optional.ofNullable(quizzesQueue.peek());
    }

    private AnswerSubmissionResult handleFailedAnswerOfRedisQueue(String sessionId) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
        SessionQuizzesQueue quizzesQueue = studySession.getQuizzesQueue();
        Quiz quizAnswered = quizzesQueue.poll();

        String correctAnswer = getCorrectAnswer(quizAnswered);
        quizzesQueue.add(quizAnswered);

        return new AnswerSubmissionFailed(quizAnswered.getWordId(), correctAnswer);
    }

    private AnswerSubmissionResult submitAnswerToRedisQueue(String sessionId, AnswerSubmission answer) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
        SessionQuizzesQueue quizzesQueue = studySession.getQuizzesQueue();
        Quiz quizAnswered = quizzesQueue.poll();

        String correctAnswer = getAnswerIfIncorrect(answer.getAnswer(), quizAnswered);
        if (correctAnswer != null) {
            quizzesQueue.add(quizAnswered);
            return new AnswerSubmissionFailed(quizAnswered.getWordId(), correctAnswer);
        }

        Integer score = calculateQuizScore(answer.getTimeAnsweredSec(), quizAnswered.getMaxTimeSec());
        return new CorrectAnswerSubmissionResult(quizAnswered.getWordId(), score);
    }

    private SessionQuizzesQueue createRedisQuizzesQueue(String sessionId, Set<Integer> wordIds) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
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
