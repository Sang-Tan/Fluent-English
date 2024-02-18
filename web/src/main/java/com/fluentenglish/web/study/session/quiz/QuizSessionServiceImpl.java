package com.fluentenglish.web.study.session.quiz;

import com.fluentenglish.web.study.session.RedisUserStudySessionDao;
import com.fluentenglish.web.study.session.StudySession;
import com.fluentenglish.web.study.session.quiz.answer.Answer;
import com.fluentenglish.web.study.session.quiz.answer.input.InputAnswer;
import com.fluentenglish.web.study.session.quiz.answer.multiplechoice.MultipleChoiceAnswer;
import com.fluentenglish.web.study.session.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.quiz.dto.AnsweredSubmission;
import com.fluentenglish.web.study.session.quiz.generator.QuizGenerateService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizSessionServiceImpl implements QuizSessionService{
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
    public Integer submitAnswer(String sessionId, AnswerSubmission answer) {
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

    private Integer submitAnswerToRedisQueue(String sessionId, AnswerSubmission answer) {
        StudySession studySession = redisUserStudySessionDao.getSessionById(sessionId);
        SessionQuizzesQueue quizzesQueue = studySession.getQuizzesQueue();
        Quiz quizAnswered = quizzesQueue.poll();

        if(answer instanceof AnsweredSubmission answeredSubmission
                && isAnswerSubmissionCorrect(answeredSubmission, quizAnswered)){
                return calculateQuizScore(answeredSubmission.getTimeAnsweredSec(), quizAnswered.getMaxTimeSec());
        }

        quizzesQueue.add(quizAnswered);
        return 0;
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

    private Boolean isAnswerSubmissionCorrect(AnsweredSubmission answeredSubmission, Quiz quizAnswered){
        Answer answer = quizAnswered.getAnswer();
        if (answer instanceof InputAnswer) {
            return ((InputAnswer) quizAnswered.getAnswer())
                    .getAnswer().equals(answeredSubmission.getAnswer());
        } else if (answer instanceof MultipleChoiceAnswer) {
            return ((MultipleChoiceAnswer) quizAnswered.getAnswer())
                    .getCorrectChoice() == Integer.parseInt(answeredSubmission.getAnswer());
        }

        return false;
    }


    private Integer calculateQuizScore(Integer timeAnsweredSec, Integer maxTimeSec){
        if(timeAnsweredSec > maxTimeSec){
            throw new IllegalArgumentException("Answer submission time exceeded max time");
        }

        double timeRatio = (double) timeAnsweredSec / maxTimeSec;
        return Math.toIntExact(Math.round(10 - Math.pow(timeRatio, 1.5) * 10));
    }
}
