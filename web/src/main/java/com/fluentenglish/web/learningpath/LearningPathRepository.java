package com.fluentenglish.web.learningpath;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LearningPathRepository extends JpaRepository<LearningPath, Integer> {
    @Query("SELECT COUNT(lp) > 0 FROM LearningPath lp WHERE lp.name = :name AND lp.id <> :id")
    boolean existsByNameExcludeId(String name, Integer id);

    boolean existsByName(String name);
}
