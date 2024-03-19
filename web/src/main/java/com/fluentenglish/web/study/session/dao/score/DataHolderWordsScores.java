package com.fluentenglish.web.study.session.dao.score;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataHolderWordsScores implements SessionWordsScores {
    private final Map<Integer, WordScore> wordsScores = new HashMap<>();

    @Override
    public Optional<WordScore> getWordScore(int wordId) {
        return Optional.ofNullable(wordsScores.get(wordId));
    }

    @Override
    public Map<Integer, WordScore> getWordsScores() {
        return wordsScores;
    }

    @Override
    public void setWordScore(int wordId, WordScore wordScore) {
        wordsScores.put(wordId, wordScore);
    }

    @Override
    public void setWordsScores(Map<Integer, WordScore> wordScores) {
        wordsScores.clear();
        wordsScores.putAll(wordScores);
    }

    @Override
    public void removeWordScore(int wordId) {
        wordsScores.remove(wordId);
    }
}
