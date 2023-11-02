package com.fluentenglish.web.learningmaterial.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("UPDATE Lesson l SET l.position = ?2 WHERE l.id = ?1")
    void updatePosition(int id, int newPosition);
}
