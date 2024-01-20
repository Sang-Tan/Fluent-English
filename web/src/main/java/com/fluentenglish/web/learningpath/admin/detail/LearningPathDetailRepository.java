package com.fluentenglish.web.learningpath.admin.detail;

import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LearningPathDetailRepository extends JpaRepository<LearningPathDetail, LearningPathDetailId> {
    @Query("SELECT lesson FROM Lesson lesson " +
            "WHERE lesson.id IN (SELECT lpd.lesson.id FROM LearningPathDetail lpd WHERE lpd.learningPath.id = :#{#learningPathId})")
    List<Lesson> findLessonsByLearningPathId(Integer learningPathId);

    List<LearningPathDetail> findAllByLearningPathId(Integer learningPathId);
    @Query("SELECT COALESCE(MAX(lpd.position), 0) FROM LearningPathDetail lpd WHERE lpd.learningPath.id = :#{#learningPathId}")
    Integer findMaxPositionByLearningPathId(int learningPathId);

    @Query("SELECT lpd FROM LearningPathDetail lpd WHERE lpd.learningPath.id = :#{#learningPathId} AND lpd.lesson.id = :#{#lessonId}")
    LearningPathDetail findByLearningPathIdAndLessonId(int learningPathId, int lessonId);

    @Query("SELECT lesson FROM Lesson lesson " +
            "WHERE lesson.id NOT IN (SELECT lpd.lesson.id FROM LearningPathDetail lpd WHERE lpd.learningPath.id = :#{#learningPathId})")
    Page<Lesson> findUnassignedLesson(Integer learningPathId, Pageable pageable);
    @Query("SELECT lesson FROM Lesson lesson " +
            "WHERE lesson.id NOT IN (SELECT lpd.lesson.id FROM LearningPathDetail lpd WHERE lpd.learningPath.id = :#{#learningPathId}) " +
            "AND (:#{#lessonSearchDto.name} IS NULL OR lesson.name LIKE %:#{#lessonSearchDto.name}%)")
    Page<Lesson> searchUnassignedLesson(LessonSearchDto lessonSearchDto, Integer learningPathId, Pageable pageable);
}
