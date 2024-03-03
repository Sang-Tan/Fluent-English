package com.fluentenglish.web.study.session.dao.score;

import java.util.Map;
import java.util.Optional;

public interface SessionWordsScores {

    Optional<WordScore> getWordScore(int wordId);

    Map<Integer, WordScore> getWordsScores();

    void setWordScore(int wordId, WordScore wordScore);

    void setWordsScores(Map<Integer, WordScore> wordScores);

    void removeWordScore(int wordId);
}
