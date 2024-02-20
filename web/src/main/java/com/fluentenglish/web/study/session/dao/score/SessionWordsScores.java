package com.fluentenglish.web.study.session.dao.score;

import java.util.Map;

public interface SessionWordsScores {

    /**
     * @throws WordNotFoundException if word score with given id does not exist
     */
    WordScore getWordScore(int wordId);

    Map<Integer, WordScore> getWordsScores();

    void setWordScore(int wordId, WordScore wordScore);

    void setWordsScores(Map<Integer, WordScore> wordScores);

    void removeWordScore(int wordId);
}
