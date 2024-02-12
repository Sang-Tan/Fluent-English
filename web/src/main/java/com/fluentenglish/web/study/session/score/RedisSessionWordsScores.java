package com.fluentenglish.web.study.session.score;

import com.fluentenglish.web.study.session.RedisStudySessionObject;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class RedisSessionWordsScores extends RedisStudySessionObject implements SessionWordsScores {
    private final RedisTemplate<String, Object> redisTemplate;

    private final HashOperations<String, Integer, String> hashOperations;

    public RedisSessionWordsScores(RedisTemplate<String, Object> redisTemplate,
                                   HashOperations<String, Integer, String> hashOperations) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = hashOperations;
    }

    @Override
    public WordScore getWordScore(int wordId) {
        String wordScore = hashOperations.get(getWordScoreKey(), wordId);
        if (wordScore == null) {
            throw new RuntimeException("Word score's key does not exist or the it " +
                    "has been set in the same transaction");
        }

        return stringToWordScore(wordScore);
    }

    @Override
    public Map<Integer, WordScore> getWordsScores() {
        Map<Integer, String> scoreMap = hashOperations.entries(getWordScoreKey());
        return scoreMap.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> stringToWordScore(entry.getValue())
                        )
                );
    }

    @Override
    public void setWordScore(int wordId, WordScore wordScore) {
        hashOperations.put(getWordScoreKey(), wordId, wordScoreToString(wordScore));
    }

    @Override
    public void setWordsScores(Map<Integer, WordScore> wordScores) {
        Map<Integer, String> stringWordScores = wordScores.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> wordScoreToString(entry.getValue())
                        )
                );
        hashOperations.putAll(getWordScoreKey(), stringWordScores);
    }

    @Override
    public void removeWordScore(int wordId) {
        hashOperations.delete(getWordScoreKey(), wordId);
    }

    @Override
    public void delete() {
        redisTemplate.delete(getWordScoreKey());
    }

    private String getWordScoreKey() {
        return String.format("studySession:%s:wordsScores", getSessionMetadata().sessionId());
    }

    private WordScore stringToWordScore(String wordScore) {
        String[] wordScoreArray = wordScore.split("\\|");
        WordScore score = new WordScore();
        score.setTotalScore(Integer.parseInt(wordScoreArray[0]));
        score.setAttempts(Integer.parseInt(wordScoreArray[1]));
        return score;
    }

    private String wordScoreToString(WordScore wordScore) {
        return wordScore.getTotalScore() + "|" + wordScore.getAttempts();
    }
}
