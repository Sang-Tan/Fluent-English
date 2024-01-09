package com.fluentenglish.web.learningmaterial.lesson;

import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("SELECT t FROM Lesson t WHERE lower(t.name) LIKE lower(concat('%', :#{#lessonSearchDto.name}, '%'))")
    List<Lesson> searchLessons(LessonSearchDto lessonSearchDto);

    boolean existsByName(String name);
}
