package com.fluentenglish.web.study.session;

import com.fluentenglish.web.study.session.battle.SessionBattle;
import com.fluentenglish.web.study.session.quiz.SessionQuizzesQueue;
import com.fluentenglish.web.study.session.score.SessionWordsScores;

public interface StudySession {
    String getId();
    int getUserId();
    SessionBattle getBattle();
    SessionQuizzesQueue getQuizzesQueue();
    SessionWordsScores getWordsScores();
}
