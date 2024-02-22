package com.fluentenglish.web.gaming.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LevelProgressDto {
    private Integer level;
    private Integer experience;
    private int expToNextLevel;
}
