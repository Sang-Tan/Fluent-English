package com.fluentenglish.web.learningmaterial.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    @Query("UPDATE Exercise e SET e.position = ?2 WHERE e.id = ?1")
    void updatePosition(int id, int newPosition);

    @Query("SELECT e.lesson.id FROM Exercise e WHERE e.id = ?1")
    int getLessonIdOfExercise(int exerciseId);

    @Query("SELECT MAX(e.position) FROM Exercise e WHERE e.lesson.id = ?1")
    Optional<Integer> getMaxPositionOfExercisesInLesson(int lessonId);
}
