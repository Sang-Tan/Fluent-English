package com.fluentenglish.web.spacedrepetition.fsrs;

public interface FSRSScheduler {
    /**
     * Get the material memo for the given grade.
     * This method is used to get the material memo for the first time.
     */
    MaterialMemoDto learnMaterial(Grade grade, long now);

    MaterialMemoDto learnMaterial(Grade grade, long now, MaterialMemoDto materialMemoDto);
}
