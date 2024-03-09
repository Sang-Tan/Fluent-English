package com.fluentenglish.web.gaming.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GamingDataDto {
    private LevelProgressDto levelProgress;

    private StoryProgressDto storyProgress;

    private UserAttributesDto userAttributes;

    private CurrentStateDto currentState;
}
