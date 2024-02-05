package com.fluentenglish.web.spacedrepetition.session;

import com.fluentenglish.web.spacedrepetition.quiz.QuizMemo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Session")
public class Session implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer wordId;

    private List<QuizMemo> quizMemos;
}
