package com.fluentenglish.web.gaming.user.dto;

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
