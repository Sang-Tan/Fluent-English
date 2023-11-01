package com.fluentenglish.web.learningmaterial.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("UPDATE Lesson l SET l.position = ?2 WHERE l.id = ?1")
    void updatePosition(int id, int newPosition);

    @Query("SELECT l.topic.id FROM Lesson l WHERE l.id = ?1")
    int getTopicIdOfLesson(int lessonId);

    @Query("SELECT MAX(l.position) FROM Lesson l WHERE l.topic.id = ?1")
    Optional<Integer> getMaxPositionOfLessonsInTopic(int topicId);
}
