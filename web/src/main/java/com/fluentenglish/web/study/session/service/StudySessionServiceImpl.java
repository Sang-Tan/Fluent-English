package com.fluentenglish.web.study.session.service;

import com.fluentenglish.web.study.session.dao.StudySession;
import com.fluentenglish.web.study.session.dao.UserStudySessionDao;
import com.fluentenglish.web.study.session.dao.battle.BattleInfo;
import com.fluentenglish.web.study.session.dao.quiz.Quiz;
import com.fluentenglish.web.study.session.service.battle.BattleService;
import com.fluentenglish.web.study.session.service.battle.dto.BattleResult;
import com.fluentenglish.web.study.session.service.dto.StudySessionActivationDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionSubmissionDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionSummaryDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionUpdateDto;
import com.fluentenglish.web.study.session.service.exception.SessionActiveException;
import com.fluentenglish.web.study.session.service.interaction.StudySessionInteractionService;
import com.fluentenglish.web.study.session.service.quiz.QuizSessionService;
import com.fluentenglish.web.study.session.service.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.service.quiz.dto.resp.AnswerSubmissionResult;
import com.fluentenglish.web.study.session.service.spacedrepetition.SRSessionService;
import com.fluentenglish.web.study.session.service.spacedrepetition.WordsScoresResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class StudySessionServiceImpl implements StudySessionService {
    private final UserStudySessionDao userStudySessionDao;

    private final QuizSessionService quizSessionService;

    private final SRSessionService srSessionService;

    private final BattleService battleService;

    private final StudySessionInteractionService studySessionInteractionService;


    public StudySessionServiceImpl(UserStudySessionDao userStudySessionDao,
                                   QuizSessionService quizSessionService,
                                   SRSessionService srSessionService,
                                   BattleService battleService,
                                   StudySessionInteractionService studySessionInteractionService) {
        this.userStudySessionDao = userStudySessionDao;
        this.quizSessionService = quizSessionService;
        this.srSessionService = srSessionService;
        this.battleService = battleService;
        this.studySessionInteractionService = studySessionInteractionService;
    }

    @Override
    public StudySessionActivationDto startStudySession(int userId, Set<Integer> wordIds) {
        StudySession studySession = userStudySessionDao.createSession(userId);

        Quiz firstQuiz = quizSessionService.initializeQuizzes(studySession.getId(), wordIds);
        BattleInfo battleInfo = battleService.initializeBattle(studySession.getId());
        studySessionInteractionService.setLastInteractionTime(studySession.getId(), System.currentTimeMillis());

        int remainingQuizzesCount = quizSessionService.countRemainingQuizzes(studySession.getId());

        StudySessionActivationDto sessionInfo = new StudySessionActivationDto();
        sessionInfo.setNextQuiz(firstQuiz);
        sessionInfo.setBattleInfo(battleInfo);
        sessionInfo.setSessionId(studySession.getMetadata().accessSessionId());
        sessionInfo.setRemainingQuizzesCount(remainingQuizzesCount);

        return sessionInfo;
    }

    @Override
    public StudySessionActivationDto reactivateStudySession(int userId) {
        StudySession studySession = userStudySessionDao.getSessionByUserId(userId);
        String studySessionId = studySession.getId();

        if ((studySessionInteractionService.isSessionActive(studySessionId))) {
            throw new SessionActiveException();
        }
        String newSessionAccessId = userStudySessionDao.renewSessionAccessId(studySession.getMetadata().sessionId());

        Optional<Quiz> quizOpt = quizSessionService.getNextQuiz(studySessionId);
        StudySessionActivationDto activationDto = new StudySessionActivationDto();
        activationDto.setSessionId(newSessionAccessId);
        activationDto.setRemainingQuizzesCount(quizSessionService.countRemainingQuizzes(studySessionId));
        activationDto.setNextQuiz(quizOpt.orElse(null));
        activationDto.setBattleInfo(battleService.getBattleInfo(studySessionId));

        studySessionInteractionService.setLastInteractionTime(studySessionId, System.currentTimeMillis());

        return activationDto;
    }

    @Override
    public boolean studySessionExists(String sessionId) {
        return userStudySessionDao.studySessionExists(sessionId);
    }

    @Override
    public StudySessionSubmissionDto submitAnswer(String sessionId, AnswerSubmission answer) {
        AnswerSubmissionResult result = quizSessionService.submitAnswer(sessionId, answer);
        studySessionInteractionService.setLastInteractionTime(sessionId, System.currentTimeMillis());
        return handleResult(sessionId, result);
    }

    @Override
    public StudySessionSubmissionDto handleFailedAnswerSubmission(String sessionId) {
        AnswerSubmissionResult result = quizSessionService.handleFailedAnswerSubmission(sessionId);
        studySessionInteractionService.setLastInteractionTime(sessionId, System.currentTimeMillis());
        return handleResult(sessionId, result);
    }

    private StudySessionSubmissionDto handleResult(String sessionId, AnswerSubmissionResult result) {
        StudySession studySession = userStudySessionDao.getSessionById(sessionId);
        String studySessionId = studySession.getId();

        int score = result.getScore();

        srSessionService.addAttempt(studySessionId, result.getWordId(), score);
        battleService.updateBattle(studySessionId, score);

        Optional<Quiz> quizOpt = quizSessionService.getNextQuiz(studySessionId);
        return quizOpt
                .map(quiz -> {
                    StudySessionUpdateDto updateInfo = continueStudySession(studySessionId, quiz, score);
                    updateInfo.setAnswerSubmissionResult(result);
                    return (StudySessionSubmissionDto) updateInfo;
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
        sessionInfo.setBattleUpdateInfo(battleService.updateBattle(studySessionId, score));
        sessionInfo.setRemainingQuizzesCount(quizSessionService.countRemainingQuizzes(studySessionId));

        return sessionInfo;
    }
}
