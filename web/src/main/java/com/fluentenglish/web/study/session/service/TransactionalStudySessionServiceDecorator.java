package com.fluentenglish.web.study.session.service;

import com.fluentenglish.web.study.session.context.StudySessionContext;
import com.fluentenglish.web.study.session.dao.RedisStudySessionDao;
import com.fluentenglish.web.study.session.service.dto.StudySessionActivationDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionSubmissionDto;
import com.fluentenglish.web.study.session.service.quiz.dto.AnswerSubmission;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Primary
public class TransactionalStudySessionServiceDecorator implements StudySessionService {
    private final StudySessionServiceImpl delegate;

    private final StudySessionContext studySessionContext;

    private final RedisStudySessionDao studySessionDao;

    public TransactionalStudySessionServiceDecorator(StudySessionServiceImpl delegate,
                                                     StudySessionContext studySessionContext,
                                                     RedisStudySessionDao studySessionDao) {
        this.delegate = delegate;
        this.studySessionContext = studySessionContext;
        this.studySessionDao = studySessionDao;
    }

    @Override
    public StudySessionActivationDto startStudySession(int userId, Set<Integer> wordIds) {
        studySessionContext.beginTransaction();

        try {
            StudySessionActivationDto activationDto =
                    delegate.startStudySession(userId, wordIds);
            studySessionDao.save();
            return activationDto;
        } finally {
            studySessionContext.endTransaction();
        }
    }

    @Override
    public StudySessionActivationDto reactivateStudySession(int userId) {
        studySessionContext.beginTransaction();

        try {
            StudySessionActivationDto activationDto =
                    delegate.reactivateStudySession(userId);
            studySessionDao.save();
            return activationDto;
        } finally {
            studySessionContext.endTransaction();
        }
    }

    @Override
    public boolean studySessionExists(String sessionId) {
        return delegate.studySessionExists(sessionId);
    }

    @Override
    public StudySessionSubmissionDto submitAnswer(String sessionId, AnswerSubmission answer) {
        studySessionContext.beginTransaction();

        try {
            StudySessionSubmissionDto submissionDto =
                    delegate.submitAnswer(sessionId, answer);
            studySessionDao.save();
            return submissionDto;
        } finally {
            studySessionContext.endTransaction();
        }
    }

    @Override
    public StudySessionSubmissionDto handleFailedAnswerSubmission(String sessionId) {
        studySessionContext.beginTransaction();

        try {
            StudySessionSubmissionDto submissionDto =
                    delegate.handleFailedAnswerSubmission(sessionId);
            studySessionDao.save();
            return submissionDto;
        } finally {
            studySessionContext.endTransaction();
        }
    }
}
