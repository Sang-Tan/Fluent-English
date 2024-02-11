package com.fluentenglish.web.study.session.battle;

import com.fluentenglish.web.study.session.RedisStudySessionObject;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RedisSessionBattle extends RedisStudySessionObject implements SessionBattle {
    private final HashMapper<Object, String, Object> hashMapper = new Jackson2HashMapper(false);

    private final RedisTemplate<String, Object> redisTemplate;

    private final HashOperations<String, String, Object> hashOperations;


    public RedisSessionBattle(RedisTemplate<String, Object> redisTemplate, HashOperations<String, String, Object> hashOperations) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = hashOperations;
    }

    @Override
    public BattleInfo getBattleInfo() {
        return (BattleInfo) hashMapper.fromHash(hashOperations.entries(getBattleInfoKey()));
    }

    @Override
    public void setBattleInfo(BattleInfo battleInfo) {
        hashOperations.putAll(getBattleInfoKey(), hashMapper.toHash(battleInfo));
    }

    @Override
    public void delete() {
        redisTemplate.delete(getBattleInfoKey());
    }

    private String getBattleInfoKey() {
        return String.format("studySession:%s:battle", getSessionMetadata().sessionId());
    }
}
