package com.fluentenglish.web.study.session.dao.last_interaction;

import com.fluentenglish.web.study.session.dao.RedisStudySessionObject;
import com.fluentenglish.web.study.session.dao.meta.StudySessionInternalMetadata;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RedisSessionLastInteraction extends RedisStudySessionObject<StudySessionInternalMetadata> implements SessionLastInteraction {
    private final RedisTemplate<String, Object> redisTemplate;

    private final ValueOperations<String, Long> longValueOperations;

    public RedisSessionLastInteraction(RedisTemplate<String, Object> redisTemplate,
                                       RedisTemplate<String, Long> longRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.longValueOperations = longRedisTemplate.opsForValue();
    }

    @Override
    public long getLastInteractionTime() {
        Long interactionTime = longValueOperations.get(getLastInteractionKey());
        if (interactionTime == null) {
            throw new RuntimeException("Last interaction time is not set");
        }

        return interactionTime;
    }

    @Override
    public void setLastInteractionTime(long lastInteractionTime) {
        longValueOperations.set(getLastInteractionKey(), lastInteractionTime);
    }

    @Override
    public void delete() {
        redisTemplate.delete(getLastInteractionKey());
    }

    private String getLastInteractionKey() {
        return String.format("studySession:%s:lastInteraction", getSessionMetadata().sessionId());
    }
}
