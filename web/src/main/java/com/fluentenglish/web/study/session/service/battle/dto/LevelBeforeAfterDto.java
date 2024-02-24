package com.fluentenglish.web.study.session.service.battle.dto;

import com.fluentenglish.web.gaming.user.dto.LevelProgressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LevelBeforeAfterDto {
    private LevelProgressDto before;
    private LevelProgressDto after;
}
