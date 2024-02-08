package com.fluentenglish.web.gaming.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LevelProgressDto {
    private Integer level;
    private Integer experience;
}
