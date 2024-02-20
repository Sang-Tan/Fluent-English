package com.fluentenglish.web.study.session.service;

import com.fluentenglish.web.study.session.StudySession;
import com.fluentenglish.web.study.session.UserStudySessionDao;
import com.fluentenglish.web.study.session.battle.BattleInfo;
import com.fluentenglish.web.study.session.battle.BattleResult;
import com.fluentenglish.web.study.session.battle.BattleService;
import com.fluentenglish.web.study.session.quiz.Quiz;
import com.fluentenglish.web.study.session.quiz.QuizSessionService;
import com.fluentenglish.web.study.session.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.quiz.dto.resp.AnswerSubmissionResult;
import com.fluentenglish.web.study.session.service.dto.StudySessionSubmissionDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionInitializationDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionSummaryDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionUpdateDto;
import com.fluentenglish.web.study.spacedrepetition.SRSessionService;
import com.fluentenglish.web.study.spacedrepetition.WordsScoresResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class StudySessionServiceImpl implements StudySessionService {
    private final UserStudySessionDao userStudySessionDao;

    private final QuizSessionService quizSessionService;

    private final SRSessionService srSessionService;

    private final BattleService battleService;

    public StudySessionServiceImpl(UserStudySessionDao userStudySessionDao,
                                   QuizSessionService quizSessionService,
                                   SRSessionService srSessionService,
                                   BattleService battleService) {
        this.userStudySessionDao = userStudySessionDao;
        this.quizSessionService = quizSessionService;
        this.srSessionService = srSessionService;
        this.battleService = battleService;
    }

    @Override
    public StudySessionInitializationDto startStudySession(int userId, Set<Integer> wordIds) {
        String studySessionId = userStudySessionDao.createSession(userId).getId();

        Quiz firstQuiz = quizSessionService.initializeQuizzes(studySessionId, wordIds);
        BattleInfo battleInfo = battleService.initializeBattle(studySessionId);

        int remainingQuizzesCount = quizSessionService.countRemainingQuizzes(studySessionId);

        StudySessionInitializationDto sessionInfo = new StudySessionInitializationDto();
        sessionInfo.setNextQuiz(firstQuiz);
        sessionInfo.setBattleInfo(battleInfo);
        sessionInfo.setSessionId(studySessionId);
        sessionInfo.setRemainingQuizzesCount(remainingQuizzesCount);

        return sessionInfo;
    }

    @Override
    public StudySessionUpdateDto continueStudySession(String sessionId) {
        //TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean studySessionExists(String sessionId) {
        return userStudySessionDao.studySessionExists(sessionId);
    }

    @Override
    public StudySessionSubmissionDto submitAnswer(String sessionId, AnswerSubmission answer) {
        StudySession studySession = userStudySessionDao.getSessionById(sessionId);
        String studySessionId = studySession.getId();

        AnswerSubmissionResult result = quizSessionService.submitAnswer(studySessionId, answer);
        int score = result.getScore();

        srSessionService.addAttempt(studySessionId, answer.getWordId(), score);
        battleService.updateBattle(studySessionId, score);

        Optional<Quiz> quizOpt = quizSessionService.getNextQuiz(studySessionId);
        return quizOpt
                .map(quiz -> {
                    StudySessionUpdateDto updateInfo = continueStudySession(studySessionId, quiz, score);
                    updateInfo.setAnswerSubmissionResult(result);
                    return (StudySessionSubmissionDto)updateInfo;
                })
                .orElseGet(() -> {
                    StudySessionSummaryDto summary = endStudySession(studySessionId);
                    summary.setAnswerSubmissionResult(result);
                    return summary;
                });
    }

    private StudySessionSummaryDto endStudySession(String studySessionId) {
        StudySessionSummaryDto summary = new StudySessionSummaryDto();

        WordsScoresResult wordsScoresResult = srSessionService.endSession(studySessionId);
        BattleResult battleResult = battleService.endBattle(studySessionId);

        summary.setWordsScores(wordsScoresResult.getScoresByWords());
        summary.setBattleResult(battleResult);

        userStudySessionDao.deleteStudySession(studySessionId);

        return summary;
    }

    private StudySessionUpdateDto continueStudySession(String studySessionId, Quiz nextQuiz, int score) {
        StudySessionUpdateDto sessionInfo = new StudySessionUpdateDto();
        sessionInfo.setNextQuiz(nextQuiz);
        sessionInfo.setBattleInfo(battleService.updateBattle(studySessionId, score));
        sessionInfo.setRemainingQuizzesCount(quizSessionService.countRemainingQuizzes(studySessionId));

        return sessionInfo;
    }
}
