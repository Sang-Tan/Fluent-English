package com.fluentenglish.web.study.session.dao;

import com.fluentenglish.web.study.session.dao.battle.RedisSessionBattle;
import com.fluentenglish.web.study.session.dao.battle.SessionBattle;
import com.fluentenglish.web.study.session.dao.quiz.RedisSessionQuizzesQueue;
import com.fluentenglish.web.study.session.dao.quiz.SessionQuizzesQueue;
import com.fluentenglish.web.study.session.dao.score.RedisSessionWordsScores;
import com.fluentenglish.web.study.session.dao.score.SessionWordsScores;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RedisStudySession extends RedisStudySessionObject implements StudySession {
    private final BeanFactory beanFactory;

    private RedisSessionBattle sessionBattle;

    private RedisSessionQuizzesQueue sessionQuizzesQueue;

    private RedisSessionWordsScores sessionWordsScores;

    public RedisStudySession(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public String getId() {
        return getSessionMetadata().sessionId();
    }

    @Override
    public int getUserId() {
        return getSessionMetadata().userId();
    }

    @Override
    public void delete() {
        getRedisSessionBattle().delete();
        getRedisSessionQuizzesQueue().delete();
        getRedisSessionWordsScores().delete();
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

    private RedisSessionQuizzesQueue createQuizzesQueue() {
        StudySessionMetadata metadata = getSessionMetadata();
        RedisSessionQuizzesQueue sessionQuizzesQueue =
                beanFactory.getBean(RedisSessionQuizzesQueue.class);
        sessionQuizzesQueue.setSessionMetadata(metadata);

        return sessionQuizzesQueue;
    }

    private RedisSessionWordsScores createWordsScores() {
        StudySessionMetadata metadata = getSessionMetadata();
        RedisSessionWordsScores sessionWordsScores =
                beanFactory.getBean(RedisSessionWordsScores.class);
        sessionWordsScores.setSessionMetadata(metadata);

        return sessionWordsScores;
    }

    private RedisSessionBattle createBattle() {
        StudySessionMetadata metadata = getSessionMetadata();
        RedisSessionBattle sessionBattle =
                beanFactory.getBean(RedisSessionBattle.class);
        sessionBattle.setSessionMetadata(metadata);

        return sessionBattle;
    }
}
