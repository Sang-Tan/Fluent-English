package com.fluentenglish.web.study.session.score;

public interface SessionWordsScores {

    /**
     * @throws WordNotFoundException if word score with given id does not exist
     */
    WordScore getWordScore(int wordId);

    void setWordScore(int wordId, WordScore wordScore);

    void removeWordScore(int wordId);
}
