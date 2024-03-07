package com.fluentenglish.web.study.session.dao.score;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RedisMetadataDto {
    private int userId;
    private String sessionAccessId;
}
