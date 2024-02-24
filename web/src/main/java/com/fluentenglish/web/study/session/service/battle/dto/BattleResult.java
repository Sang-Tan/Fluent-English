package com.fluentenglish.web.study.session.service.battle.dto;

import com.fluentenglish.web.gaming.user.dto.CurrentStateDto;
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
