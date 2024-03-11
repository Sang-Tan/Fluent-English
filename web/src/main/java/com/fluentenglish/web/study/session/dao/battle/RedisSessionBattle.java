package com.fluentenglish.web.study.session.dao.battle;

import com.fluentenglish.web.study.session.dao.RedisStudySessionObject;
import com.fluentenglish.web.study.session.dao.meta.StudySessionInternalMetadata;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RedisSessionBattle extends RedisStudySessionObject<StudySessionInternalMetadata> implements SessionBattle {
    private final HashMapper<BattleInfo, String, String> hashMapper
            = new BeanUtilsHashMapper<>(BattleInfo.class);

    private final StringRedisTemplate redisTemplate;

    private final HashOperations<String, String, String> hashOperations;


    public RedisSessionBattle(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public BattleInfo getBattleInfo() {
        return hashMapper.fromHash(hashOperations.entries(getBattleInfoKey()));
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
