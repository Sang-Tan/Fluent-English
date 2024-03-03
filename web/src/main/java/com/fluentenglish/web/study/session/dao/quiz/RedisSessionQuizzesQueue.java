package com.fluentenglish.web.study.session.dao.quiz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentenglish.web.study.session.dao.RedisStudySessionObject;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
public class RedisSessionQuizzesQueue extends RedisStudySessionObject implements SessionQuizzesQueue {
    private final RedisTemplate<String, Object> redisTemplate;

    private final ListOperations<String, String> listOperations;

    private final ObjectMapper objectMapper;

    public RedisSessionQuizzesQueue(RedisTemplate<String, Object> objectTemplate,
                                    RedisTemplate<String, String> stringTemplate,
                                    ObjectMapper objectMapper) {
        this.redisTemplate = objectTemplate;
        this.listOperations = stringTemplate.opsForList();
        this.objectMapper = objectMapper;
    }

    @Override
    public void add(List<Quiz> quizzes) {
        quizzes.forEach(this::add);
    }

    @Override
    public void add(Quiz quiz) {
        try {
            listOperations.rightPush(getQuizzesQueueKey(), objectMapper.writeValueAsString(quiz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int quizzesCount() {
        return Objects.requireNonNull(listOperations.size(getQuizzesQueueKey())).intValue();
    }

    @Override
    public Quiz poll() {
        String val = listOperations.leftPop(getQuizzesQueueKey());
        if (val == null) {
            return null;
        }

        try {
            return objectMapper.readValue(val, Quiz.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Quiz peek() {
        String val = listOperations.index(getQuizzesQueueKey(), 0);
        if (val == null) {
            return null;
        }

        try {
            return objectMapper.readValue(val, Quiz.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete() {
        redisTemplate.delete(getQuizzesQueueKey());
    }

    private String getQuizzesQueueKey() {
        return String.format("studySession:%s:quizzes", getSessionMetadata().sessionId());
    }
}
