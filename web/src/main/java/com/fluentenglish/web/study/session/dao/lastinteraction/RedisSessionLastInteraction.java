package com.fluentenglish.web.study.session.dao.lastinteraction;

import com.fluentenglish.web.study.session.dao.RedisStudySessionObject;
import com.fluentenglish.web.study.session.dao.meta.StudySessionInternalMetadata;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope("prototype")
public class RedisSessionLastInteraction extends RedisStudySessionObject<StudySessionInternalMetadata> implements SessionLastInteraction {
    private final StringRedisTemplate redisTemplate;

    private final ValueOperations<String, String> valueOperations;

    public RedisSessionLastInteraction(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public long getLastInteractionTime() {
        return getOptLastInteractionTime()
                .orElseThrow(() -> new RuntimeException("Last interaction time is not set"));
    }

    @Override
    public void setLastInteractionTime(long lastInteractionTime) {
        valueOperations.set(getLastInteractionKey(), String.valueOf(lastInteractionTime));
    }

    @Override
    public void delete() {
        redisTemplate.delete(getLastInteractionKey());
    }

    private String getLastInteractionKey() {
        return String.format("studySession:%s:lastInteraction", getSessionMetadata().sessionId());
    }

    private Optional<Long> getOptLastInteractionTime() {
        String lastInteractionString = valueOperations.get(getLastInteractionKey());
        return Optional.ofNullable(lastInteractionString).map(Long::valueOf);
    }
}
