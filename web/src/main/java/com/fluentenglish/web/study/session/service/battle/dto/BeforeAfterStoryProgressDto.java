package com.fluentenglish.web.study.session.service.battle.dto;

import com.fluentenglish.web.gaming.user.dto.StoryContentDto;
import com.fluentenglish.web.gaming.user.dto.StoryProgressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BeforeAfterStoryProgressDto {
    private StoryProgressDto before;

    private StoryProgressDto after;

    private List<StoryContentDto> storyContentReceived;
}
