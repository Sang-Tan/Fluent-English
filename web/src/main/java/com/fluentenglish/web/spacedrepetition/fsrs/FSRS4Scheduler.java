package com.fluentenglish.web.spacedrepetition.fsrs;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FSRS4Scheduler implements FSRSScheduler {
    private final float[] params;

    private final float requestRetention;

    private final float minDifficulty;

    private final float maxDifficulty;

    public FSRS4Scheduler(FSRSConfiguration configuration) {
        this.params = configuration.getParams();
        this.requestRetention = configuration.getRequestRetention();
        this.minDifficulty = configuration.getMinDifficulty();
        this.maxDifficulty = configuration.getMaxDifficulty();
    }

    @Override
    public MaterialMemoDto learnMaterial(Grade grade, long now) {
        MaterialMemoDto materialMemoDto = new MaterialMemoDto();
        materialMemoDto.setStability(initialStability(grade));
        materialMemoDto.setDifficulty(initialDifficulty(grade));
        materialMemoDto.setLastStudy(new Date(now));
        materialMemoDto.setNextStudy(new Date(now + newIntervalMs(materialMemoDto.getStability())));

        return materialMemoDto;
    }

    @Override
    public MaterialMemoDto learnMaterial(Grade grade, long now, MaterialMemoDto materialMemoDto) {
        MaterialMemoDto newMaterialMemoDto = new MaterialMemoDto();
        float lastDifficulty = materialMemoDto.getDifficulty();
        float lastStability = materialMemoDto.getStability();

        newMaterialMemoDto.setDifficulty(newDifficulty(materialMemoDto.getDifficulty(), grade.getValue()));
        newMaterialMemoDto.setStability(
                newStability(lastStability,
                        lastDifficulty,
                        retrievability(dayElapsed(materialMemoDto.getLastStudy().getTime(), now), lastStability),
                        grade));
        newMaterialMemoDto.setLastStudy(new Date(now));
        newMaterialMemoDto.setNextStudy(new Date(now + newIntervalMs(newMaterialMemoDto.getStability())));

        return newMaterialMemoDto;
    }

    private float initialStability(Grade grade) {
        return params[grade.getValue() - 1];
    }

    private float initialDifficulty(Grade grade) {
        return params[4] - (grade.getValue() - 3) * params[5];
    }

    private float newDifficulty(float lastDifficulty, int grade) {
        float newDifficulty = lastDifficulty - params[6] * (grade - 3);
        return difficultyConstraint(meanReversion(params[4], newDifficulty));
    }

    private float meanReversion(float initVal, float currentVal) {
        return params[7] * initVal + (1.0f - params[7]) * currentVal;
    }

    /**
     * @return difficulty value between minDifficulty and maxDifficulty
     */
    private float difficultyConstraint(float difficulty) {
        return Math.min(Math.max(difficulty, minDifficulty), maxDifficulty);
    }

    /**
     * @param s last stability
     * @param d last difficulty
     * @param r retrievability at current time
     * @param g grade
     */
    private float newStability(float s, float d, float r, Grade g) {
        if (Grade.AGAIN.equals(g)) {
            return forgettingStability(d, s, r);
        }

        float hardPenalty = Grade.HARD.equals(g) ? params[15] : 1.0f;
        float easyBound = Grade.EASY.equals(g) ? params[16] : 1.0f;
        return (float) (s * (
                Math.exp(params[8])
                        * (11 - d)
                        * Math.pow(s, -params[9])
                        * (Math.exp(params[10] * (1 - r)) - 1)
                        * hardPenalty * easyBound + 1
        ));
    }

    /**
     * @param s last stability
     * @param d last difficulty
     * @param r retrievability at current time
     */
    private float forgettingStability(float d, float s, float r) {
        return (float) (params[11] * Math.pow(d, -params[12])
                * (Math.pow(s + 1, params[13]) - 1)
                * Math.exp(params[14] * (1 - r))
        );
    }

    /**
     * @param t time in day since last study
     * @param s last stability
     */
    private float retrievability(float t, float s) {
        return (float) (1.0 / (1.0 + (t / (9.0 * s))));
    }

    private long newIntervalMs(float stability) {
        return (long) ((9.0f * (1.0f / requestRetention - 1.0f) * stability) * 86400000.0f);
    }

    private float dayElapsed(long lastTime, long currentTime) {
        return (currentTime - lastTime) / 86400000.0f;
    }
}
