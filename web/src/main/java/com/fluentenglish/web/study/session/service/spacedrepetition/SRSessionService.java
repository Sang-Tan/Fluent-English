package com.fluentenglish.web.study.session.service.spacedrepetition;

import java.util.Set;

public interface SRSessionService {
    void initialize(String sessionId, Set<Integer> wordIds);

    /**
     * @param score score of the attempt [0, 10]
     */
    void addAttempt(String sessionId, int wordId, int score);

    WordsScoresResult endSession(String sessionId);
}
