package com.fluentenglish.web.study.session.service;

import com.fluentenglish.web.study.session.StudySession;
import com.fluentenglish.web.study.session.UserStudySessionDao;
import com.fluentenglish.web.study.session.battle.BattleInfo;
import com.fluentenglish.web.study.session.battle.BattleResult;
import com.fluentenglish.web.study.session.battle.BattleService;
import com.fluentenglish.web.study.session.quiz.Quiz;
import com.fluentenglish.web.study.session.quiz.QuizSessionService;
import com.fluentenglish.web.study.session.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.service.dto.StudySessionInfo;
import com.fluentenglish.web.study.session.service.dto.StudySessionUpdateInfo;
import com.fluentenglish.web.study.session.service.dto.StudySessionSummary;
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
    public StudySessionUpdateInfo startStudySession(int userId, Set<Integer> wordIds) {
        String studySessionId = userStudySessionDao.createSession(userId).getId();

        Quiz firstQuiz = quizSessionService.initializeQuizzes(studySessionId, wordIds);
        BattleInfo battleInfo = battleService.initializeBattle(studySessionId);

        int remainingQuizzesCount = quizSessionService.countRemainingQuizzes(studySessionId);

        StudySessionUpdateInfo sessionInfo = new StudySessionUpdateInfo();
        sessionInfo.setNextQuiz(firstQuiz);
        sessionInfo.setBattleInfo(battleInfo);
        sessionInfo.setSessionId(studySessionId);
        sessionInfo.setRemainingQuizzesCount(remainingQuizzesCount);

        return sessionInfo;
    }

    @Override
    public StudySessionUpdateInfo continueStudySession(String sessionId) {
        //TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean studySessionExists(String sessionId) {
        return userStudySessionDao.studySessionExists(sessionId);
    }

    @Override
    public StudySessionInfo submitAnswer(String sessionId, AnswerSubmission answer) {
        StudySession studySession = userStudySessionDao.getSessionById(sessionId);
        String studySessionId = studySession.getId();

        int score = quizSessionService.submitAnswer(studySessionId, answer);
        srSessionService.addAttempt(studySessionId, answer.getWordId(), score);
        battleService.updateBattle(studySessionId, score);

        Optional<Quiz> quizOpt = quizSessionService.getNextQuiz(studySessionId);
        return quizOpt
                .map(quiz -> continueStudySession(studySessionId, quiz, score))
                .orElseGet(() -> endStudySession(studySessionId));
    }

    private StudySessionInfo endStudySession(String studySessionId) {
        StudySessionSummary summary = new StudySessionSummary();

        WordsScoresResult wordsScoresResult = srSessionService.endSession(studySessionId);
        BattleResult battleResult = battleService.endBattle(studySessionId);

        summary.setWordsScores(wordsScoresResult.getScoresByWords());
        summary.setBattleResult(battleResult);

        userStudySessionDao.deleteStudySession(studySessionId);

        return summary;
    }

    private StudySessionInfo continueStudySession(String studySessionId, Quiz nextQuiz, int score) {
        StudySessionUpdateInfo sessionInfo = new StudySessionUpdateInfo();
        sessionInfo.setNextQuiz(nextQuiz);
        sessionInfo.setBattleInfo(battleService.updateBattle(studySessionId, score));
        sessionInfo.setRemainingQuizzesCount(quizSessionService.countRemainingQuizzes(studySessionId));

        return sessionInfo;
    }
}
