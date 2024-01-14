package com.fluentenglish.web.learningmaterial.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("SELECT q FROM Quiz q WHERE q.exercise.id = ?1 ORDER BY q.position ASC")
    List<Quiz> findAllByExerciseIdOrderedByPositionAsc(Integer exerciseId);

    @Query("SELECT MAX(q.position) FROM Quiz q WHERE q.exercise.id = ?1")
    Optional<Integer> getMaxPositionOfQuizzesInExercise(Integer exerciseId);
}
