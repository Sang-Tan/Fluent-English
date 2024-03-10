package com.fluentenglish.web.study.session.dao;

import com.fluentenglish.web.study.session.dao.battle.RedisSessionBattle;
import com.fluentenglish.web.study.session.dao.battle.SessionBattle;
import com.fluentenglish.web.study.session.dao.lastinteraction.RedisSessionLastInteraction;
import com.fluentenglish.web.study.session.dao.lastinteraction.SessionLastInteraction;
import com.fluentenglish.web.study.session.dao.meta.StudySessionInternalMetadata;
import com.fluentenglish.web.study.session.dao.meta.StudySessionManageMetadata;
import com.fluentenglish.web.study.session.dao.quiz.RedisSessionQuizzesQueue;
import com.fluentenglish.web.study.session.dao.quiz.SessionQuizzesQueue;
import com.fluentenglish.web.study.session.dao.score.RedisSessionWordsScores;
import com.fluentenglish.web.study.session.dao.score.SessionWordsScores;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RedisStudySession extends RedisStudySessionObject<StudySessionManageMetadata> implements StudySession {
    private final BeanFactory beanFactory;

    private RedisSessionBattle sessionBattle;

    private RedisSessionQuizzesQueue sessionQuizzesQueue;

    private RedisSessionWordsScores sessionWordsScores;

    private RedisSessionLastInteraction sessionLastInteraction;

    public RedisStudySession(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public String getId() {
        return getSessionMetadata().accessSessionId();
    }

    @Override
    public StudySessionManageMetadata getMetadata() {
        return getSessionMetadata();
    }

    @Override
    public void delete() {
        getRedisSessionBattle().delete();
        getRedisSessionQuizzesQueue().delete();
        getRedisSessionWordsScores().delete();
        getRedisSessionLastInteraction().delete();
    }

    @Override
    public SessionBattle getBattle() {
        return getRedisSessionBattle();
    }

    @Override
    public SessionQuizzesQueue getQuizzesQueue() {
        return getRedisSessionQuizzesQueue();
    }

    @Override
    public SessionWordsScores getWordsScores() {
        return getRedisSessionWordsScores();
    }

    @Override
    public SessionLastInteraction getLastInteraction() {
        return getRedisSessionLastInteraction();
    }

    private RedisSessionBattle getRedisSessionBattle() {
        if (sessionBattle == null) {
            sessionBattle = createBattle();
        }
        return sessionBattle;
    }

    private RedisSessionQuizzesQueue getRedisSessionQuizzesQueue() {
        if (sessionQuizzesQueue == null) {
            sessionQuizzesQueue = createQuizzesQueue();
        }
        return sessionQuizzesQueue;
    }

    private RedisSessionWordsScores getRedisSessionWordsScores() {
        if (sessionWordsScores == null) {
            sessionWordsScores = createWordsScores();
        }
        return sessionWordsScores;
    }

    private RedisSessionLastInteraction getRedisSessionLastInteraction() {
        if (sessionLastInteraction == null) {
            sessionLastInteraction = createLastInteraction();
        }
        return sessionLastInteraction;
    }

    private RedisSessionQuizzesQueue createQuizzesQueue() {
        RedisSessionQuizzesQueue sessionQuizzesQueue =
                beanFactory.getBean(RedisSessionQuizzesQueue.class);
        sessionQuizzesQueue.setSessionMetadata(getInternalMetadata());

        return sessionQuizzesQueue;
    }

    private RedisSessionWordsScores createWordsScores() {
        RedisSessionWordsScores sessionWordsScores =
                beanFactory.getBean(RedisSessionWordsScores.class);
        sessionWordsScores.setSessionMetadata(getInternalMetadata());

        return sessionWordsScores;
    }

    private RedisSessionBattle createBattle() {
        RedisSessionBattle sessionBattle =
                beanFactory.getBean(RedisSessionBattle.class);
        sessionBattle.setSessionMetadata(getInternalMetadata());

        return sessionBattle;
    }

    private RedisSessionLastInteraction createLastInteraction() {
        RedisSessionLastInteraction sessionLastInteraction =
                beanFactory.getBean(RedisSessionLastInteraction.class);
        sessionLastInteraction.setSessionMetadata(getInternalMetadata());

        return sessionLastInteraction;
    }

    private StudySessionInternalMetadata getInternalMetadata() {
        return new StudySessionInternalMetadata(getSessionMetadata().sessionId());
    }
}
