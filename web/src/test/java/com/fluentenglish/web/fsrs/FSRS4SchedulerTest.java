package com.fluentenglish.web.fsrs;

import com.fluentenglish.web.spacedrepetition.fsrs.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FSRS4SchedulerTest {
    static final float EPSILON = 0.01f;

    final FSRS4Scheduler scheduler = new FSRS4Scheduler(FSRSConfiguration
            .builder()
            .requestRetention(0.9f)
            .minDifficulty(1.0f)
            .maxDifficulty(10.0f)
            .params(FSRSParams.params_v4)
            .build());

    @Test
    void test_learnMaterialFirstTime() {
        long now = 1000;

        MaterialMemoDto againMemo = scheduler.learnMaterial(Grade.AGAIN, now);
        assertApproximatelyEquals(0.4f, againMemo.getStability());
        assertApproximatelyEquals(6.81f, againMemo.getDifficulty());
        assertIntervalApproximatelyEquals(againMemo.getLastStudy(), againMemo.getNextStudy(), 0.4f);

        MaterialMemoDto hardMemo = scheduler.learnMaterial(Grade.HARD, now);
        assertApproximatelyEquals(0.6f, hardMemo.getStability());
        assertApproximatelyEquals(5.87f, hardMemo.getDifficulty());
        assertIntervalApproximatelyEquals(hardMemo.getLastStudy(), hardMemo.getNextStudy(), 0.6f);

        MaterialMemoDto goodMemo = scheduler.learnMaterial(Grade.GOOD, now);
        assertApproximatelyEquals(2.4f, goodMemo.getStability());
        assertApproximatelyEquals(4.93f, goodMemo.getDifficulty());
        assertIntervalApproximatelyEquals(goodMemo.getLastStudy(), goodMemo.getNextStudy(), 2.4f);

        MaterialMemoDto easyMemo = scheduler.learnMaterial(Grade.EASY, now);
        assertApproximatelyEquals(5.8f, easyMemo.getStability());
        assertApproximatelyEquals(3.99f, easyMemo.getDifficulty());
        assertIntervalApproximatelyEquals(easyMemo.getLastStudy(), easyMemo.getNextStudy(), 5.8f);
    }

    @Test
    void test_reviewMaterial() {
        long now = (long) (0.4 * 24 * 60 * 60 * 1000);

        MaterialMemoDto memo = new MaterialMemoDto();
        memo.setStability(0.4f);
        memo.setDifficulty(6.81f);
        memo.setLastStudy(new Date(0));
        memo.setNextStudy(new Date(now));

        MaterialMemoDto againMemo = scheduler.learnMaterial(Grade.AGAIN, now, memo);
        assertApproximatelyEquals(8.494f, againMemo.getDifficulty());
        assertApproximatelyEquals(0.2723f, againMemo.getStability());
        assertIntervalApproximatelyEquals(againMemo.getLastStudy(), againMemo.getNextStudy(), 0.2723f);

        MaterialMemoDto hardMemo = scheduler.learnMaterial(Grade.HARD, now, memo);
        assertApproximatelyEquals(7.6426f, hardMemo.getDifficulty());
        assertApproximatelyEquals(0.6417f, hardMemo.getStability());
        assertIntervalApproximatelyEquals(hardMemo.getLastStudy(), hardMemo.getNextStudy(), 0.6417f);

        MaterialMemoDto goodMemo = scheduler.learnMaterial(Grade.GOOD, now, memo);
        assertApproximatelyEquals(6.7912f, goodMemo.getDifficulty());
        assertApproximatelyEquals(1.2333f, goodMemo.getStability());
        assertIntervalApproximatelyEquals(goodMemo.getLastStudy(), goodMemo.getNextStudy(), 1.2333f);

        MaterialMemoDto easyMemo = scheduler.learnMaterial(Grade.EASY, now, memo);
        assertApproximatelyEquals(5.9398f, easyMemo.getDifficulty());
        assertApproximatelyEquals(2.5748f, easyMemo.getStability());
        assertIntervalApproximatelyEquals(easyMemo.getLastStudy(), easyMemo.getNextStudy(), 2.5748f);
    }

    void assertApproximatelyEquals(float expected, float actual) {
        assertTrue(Math.abs(expected - actual) < EPSILON);
    }

    void assertIntervalApproximatelyEquals(Date lastStudied, Date nextStudy, float expectedDay) {
        long interval = nextStudy.getTime() - lastStudied.getTime();
        double intervalDays = interval / (1000.0 * 60.0 * 60.0 * 24.0);

        // precision of 1 minute
        assertTrue(Math.abs(expectedDay - intervalDays) < (1 / (24.0 * 60.0)));
    }
}