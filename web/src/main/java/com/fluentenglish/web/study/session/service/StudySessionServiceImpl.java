package com.fluentenglish.web.study.session.service;

import com.fluentenglish.web.study.session.dao.StudySession;
import com.fluentenglish.web.study.session.dao.StudySessionAccessIdDao;
import com.fluentenglish.web.study.session.dao.StudySessionDao;
import com.fluentenglish.web.study.session.dao.quiz.Quiz;
import com.fluentenglish.web.study.session.service.battle.BattleService;
import com.fluentenglish.web.study.session.service.battle.dto.BattleInfoDto;
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
import java.util.UUID;

@Service
public class StudySessionServiceImpl implements StudySessionService {
    private final StudySessionDao studySessionDao;

    private final StudySessionAccessIdDao studySessionAccessIdDao;

    private final QuizSessionService quizSessionService;

    private final SRSessionService srSessionService;

    private final BattleService battleService;

    private final StudySessionInteractionService studySessionInteractionService;


    public StudySessionServiceImpl(StudySessionDao studySessionDao,
                                   StudySessionAccessIdDao studySessionAccessIdDao,
                                   QuizSessionService quizSessionService,
                                   SRSessionService srSessionService,
                                   BattleService battleService,
                                   com.fluentenglish.web.study.session.service.interaction.StudySessionInteractionService studySessionInteractionService) {
        this.studySessionDao = studySessionDao;
        this.studySessionAccessIdDao = studySessionAccessIdDao;
        this.quizSessionService = quizSessionService;
        this.srSessionService = srSessionService;
        this.battleService = battleService;
        this.studySessionInteractionService = studySessionInteractionService;
    }

    @Override
    public StudySessionActivationDto startStudySession(int userId, Set<Integer> wordIds) {
        StudySessionActivationDto activationDto = initializeStudySession(userId, wordIds);
        String sessionId = activationDto.getSessionId();
        studySessionInteractionService.setLastInteractionTime(sessionId,
                System.currentTimeMillis());

        String accessId = generateRandomAccessId();

        studySessionAccessIdDao
                .createAccessIdForSession(sessionId, accessId);
        activationDto.setSessionId(accessId);

        return activationDto;
    }

    @Override
    public StudySessionActivationDto reactivateStudySession(int userId) {
        String sessionId = studySessionDao.getSessionIdByUserId(userId);
        if (!studySessionInteractionService.isSessionActive(sessionId)) {
            throw new SessionActiveException();
        }

        StudySessionActivationDto activationDto = getCurrentSessionInfo(sessionId);
        studySessionInteractionService.setLastInteractionTime(sessionId,
                System.currentTimeMillis());

        String newAccessId = generateRandomAccessId();
        studySessionAccessIdDao.changeAccessIdBySessionId(sessionId, newAccessId);
        activationDto.setSessionId(newAccessId);

        return activationDto;
    }

    @Override
    public boolean studySessionExists(String sessionAccessId) {
        String sessionId = studySessionAccessIdDao.getSessionIdByAccessId(sessionAccessId);

        return studySessionDao.studySessionExists(sessionId);
    }

    @Override
    public StudySessionSubmissionDto submitAnswer(String sessionAccessId, AnswerSubmission answer) {
        String sessionId = studySessionAccessIdDao.getSessionIdByAccessId(sessionAccessId);

        AnswerSubmissionResult result = quizSessionService.submitAnswer(sessionId, answer);
        studySessionInteractionService.setLastInteractionTime(sessionId, System.currentTimeMillis());
        return handleResult(sessionId, result);
    }

    @Override
    public StudySessionSubmissionDto handleFailedAnswerSubmission(String sessionAccessId) {
        String sessionId = studySessionAccessIdDao.getSessionIdByAccessId(sessionAccessId);

        AnswerSubmissionResult result = quizSessionService.handleFailedAnswerSubmission(sessionId);
        studySessionInteractionService.setLastInteractionTime(sessionId, System.currentTimeMillis());
        return handleResult(sessionId, result);
    }

    private StudySessionActivationDto initializeStudySession(int userId, Set<Integer> wordIds) {
        StudySession studySession = studySessionDao.createSession(userId);

        Quiz firstQuiz = quizSessionService.initializeQuizzes(studySession.getId(), wordIds);
        BattleInfoDto battleInfo = battleService.initializeBattle(studySession.getId());

        int remainingQuizzesCount = quizSessionService.countRemainingQuizzes(studySession.getId());

        StudySessionActivationDto sessionInfo = new StudySessionActivationDto();
        sessionInfo.setNextQuiz(firstQuiz);
        sessionInfo.setBattleInfo(battleInfo);
        sessionInfo.setSessionId(studySession.getId());
        sessionInfo.setRemainingQuizzesCount(remainingQuizzesCount);

        return sessionInfo;
    }

    private StudySessionActivationDto getCurrentSessionInfo(String sessionId) {
        Optional<Quiz> quizOpt = quizSessionService.getNextQuiz(sessionId);

        StudySessionActivationDto activationDto = new StudySessionActivationDto();
        activationDto.setSessionId(sessionId);
        activationDto.setRemainingQuizzesCount(quizSessionService
                .countRemainingQuizzes(sessionId));
        activationDto.setNextQuiz(quizOpt.orElse(null));
        activationDto.setBattleInfo(battleService.getBattleInfo(sessionId));

        return activationDto;
    }

    private StudySessionSubmissionDto handleResult(String sessionId, AnswerSubmissionResult result) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
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

        studySessionDao.deleteStudySession(studySessionId);

        return summary;
    }

    private StudySessionUpdateDto continueStudySession(String studySessionId, Quiz nextQuiz, int score) {
        StudySessionUpdateDto sessionInfo = new StudySessionUpdateDto();
        sessionInfo.setNextQuiz(nextQuiz);
        sessionInfo.setBattleInfo(battleService.updateBattle(studySessionId, score));
        sessionInfo.setRemainingQuizzesCount(quizSessionService.countRemainingQuizzes(studySessionId));

        return sessionInfo;
    }

    private String generateRandomAccessId() {
        return UUID.randomUUID().toString();
    }
}
