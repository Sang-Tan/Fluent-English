package com.fluentenglish.web.study.session.battle;

import com.fluentenglish.web.gaming.user.dto.BeforeAfterStoryProcessDto;
import com.fluentenglish.web.gaming.user.dto.CurrentStateDto;
import com.fluentenglish.web.gaming.user.dto.LevelBeforeAfterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BattleResult {
    private BeforeAfterStoryProcessDto storyProcess;

    private LevelBeforeAfterDto levelProgress;

    private CurrentStateDto currentState;
}
