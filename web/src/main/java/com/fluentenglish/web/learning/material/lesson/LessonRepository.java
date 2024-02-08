package com.fluentenglish.web.learning.material.lesson;

import com.fluentenglish.web.learning.material.lesson.dto.LessonSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("SELECT t FROM Lesson t WHERE lower(t.name) LIKE lower(concat('%', :#{#lessonSearchDto.name}, '%'))")
    Page<Lesson> searchLessons(LessonSearchDto lessonSearchDto, Pageable pageable);

    boolean existsByName(String name);
}
