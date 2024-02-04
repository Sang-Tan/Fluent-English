package com.fluentenglish.web.spacedrepetition.fsrs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FSRSConfiguration {
    @Builder.Default
    private float[] params = FSRSParams.params_v4;

    @Builder.Default
    private float requestRetention = 0.9f;

    @Builder.Default
    private float minDifficulty = 1.0f;

    @Builder.Default
    private float maxDifficulty = 10.0f;
}
