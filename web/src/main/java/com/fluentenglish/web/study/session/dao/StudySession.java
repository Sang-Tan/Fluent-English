package com.fluentenglish.web.study.session.dao;

import com.fluentenglish.web.study.session.dao.battle.SessionBattle;
import com.fluentenglish.web.study.session.dao.last_interaction.SessionLastInteraction;
import com.fluentenglish.web.study.session.dao.quiz.SessionQuizzesQueue;
import com.fluentenglish.web.study.session.dao.score.SessionWordsScores;

public interface StudySession {
    String getId();
    int getUserId();
    SessionBattle getBattle();
    SessionQuizzesQueue getQuizzesQueue();
    SessionWordsScores getWordsScores();
    SessionLastInteraction getLastInteraction();
}
