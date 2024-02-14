package com.fluentenglish.web.gaming.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BeforeAfterStoryProcessDto {
    private StoryProcessDto before;

    private StoryProcessDto after;

    private List<StoryContentDto> storyContentReceived;
}
