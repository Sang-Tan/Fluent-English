package com.fluentenglish.web.spacedrepetition.quiz;

import com.fluentenglish.web.spacedrepetition.session.Session;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@RedisHash("QuizMemo")
public class QuizMemo implements Serializable {
    @Id
    private Integer id;
    private Integer wordId;
    private boolean isAnswered;
    private boolean isCorrect;
    private Integer timeAnsweredMs;
    private Integer maxTimeMs;
    private Integer points;

    private Session session;
}
