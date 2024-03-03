package com.fluentenglish.web.study.session.dao.score;

import com.fluentenglish.web.study.session.dao.RedisStudySessionObject;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class RedisSessionWordsScores extends RedisStudySessionObject implements SessionWordsScores {
    private final RedisTemplate<String, String> redisTemplate;

    private final HashOperations<String, String, String> hashOperations;

    public RedisSessionWordsScores(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Optional<WordScore> getWordScore(int wordId) {
        String wordScore = hashOperations.get(getWordScoreKey(), String.valueOf(wordId));

        return Optional.ofNullable(wordScore).map(this::stringToWordScore);
    }

    @Override
    public Map<Integer, WordScore> getWordsScores() {
        Map<String, String> scoreMap = hashOperations.entries(getWordScoreKey());
        return scoreMap.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> Integer.parseInt(entry.getKey()),
                                entry -> stringToWordScore(entry.getValue())
                        )
                );
    }

    @Override
    public void setWordScore(int wordId, WordScore wordScore) {
        hashOperations.put(getWordScoreKey(), String.valueOf(wordId), wordScoreToString(wordScore));
    }

    @Override
    public void setWordsScores(Map<Integer, WordScore> wordScores) {
        Map<String, String> stringWordScores = wordScores.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> String.valueOf(entry.getKey()),
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
