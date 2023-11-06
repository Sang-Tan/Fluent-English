package com.fluentenglish.web.learningmaterial.lesson.introduction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IntroductionRepository extends JpaRepository<Introduction, Integer> {
    @Query("SELECT i FROM Introduction i WHERE i.lesson.id = :lessonId")
    Optional<Introduction> findByLessonId(int lessonId);
}
