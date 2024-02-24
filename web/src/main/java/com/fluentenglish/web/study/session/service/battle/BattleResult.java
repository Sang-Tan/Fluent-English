package com.fluentenglish.web.study.session.service.battle;

import com.fluentenglish.web.gaming.user.dto.AttributesBeforeAfterDto;
import com.fluentenglish.web.gaming.user.dto.BeforeAfterStoryProgressDto;
import com.fluentenglish.web.gaming.user.dto.CurrentStateDto;
import com.fluentenglish.web.gaming.user.dto.LevelBeforeAfterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BattleResult {
    private BeforeAfterStoryProgressDto storyProgress;

    private LevelBeforeAfterDto levelProgress;

    private AttributesBeforeAfterDto attributesChange;

    private CurrentStateDto currentState;
}
