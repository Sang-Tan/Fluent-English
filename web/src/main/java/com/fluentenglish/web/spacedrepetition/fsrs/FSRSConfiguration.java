package com.fluentenglish.web.spacedrepetition.fsrs;

import lombok.*;

@Getter
@Builder
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

    public FSRSConfiguration(float[] params, float requestRetention, float minDifficulty, float maxDifficulty) {
        this.params = params;
        this.requestRetention = requestRetention;
        this.minDifficulty = minDifficulty;
        this.maxDifficulty = maxDifficulty;
    }
}
