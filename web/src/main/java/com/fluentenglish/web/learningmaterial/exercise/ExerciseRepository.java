package com.fluentenglish.web.learningmaterial.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    @Query("UPDATE Exercise e SET e.position = ?2 WHERE e.id = ?1")
    void updatePosition(int id, int newPosition);

    @Query("SELECT MAX(e.position) FROM Exercise e WHERE e.lesson.id = ?1")
    Optional<Integer> getMaxPositionOfExercisesInLesson(int lessonId);

    @Query("SELECT e FROM Exercise e WHERE e.lesson.id = ?1 ORDER BY e.position")
    List<Exercise> findAllByLessonIdOrderedByPositionAsc(int lessonId);
}
