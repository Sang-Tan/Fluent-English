package com.fluentenglish.web.learningpath;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LearningPathRepository extends JpaRepository<LearningPath, Integer> {
    boolean existsByName(String name);
}
