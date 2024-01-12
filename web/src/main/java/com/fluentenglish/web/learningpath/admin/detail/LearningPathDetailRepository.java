package com.fluentenglish.web.learningpath.admin.detail;

import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LearningPathDetailRepository extends JpaRepository<LearningPathDetail, LearningPathDetailId> {
    @Query("SELECT lesson FROM Lesson lesson " +
            "WHERE lesson.id IN (SELECT lpd.lesson.id FROM LearningPathDetail lpd WHERE lpd.learningPath.id = :#{#learningPathId})")
    List<Lesson> findLessonsByLearningPathId(Integer learningPathId);

    List<LearningPathDetail> findAllByLearningPathId(Integer learningPathId);
}
