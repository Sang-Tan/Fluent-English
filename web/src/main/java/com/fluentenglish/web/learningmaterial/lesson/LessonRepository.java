package com.fluentenglish.web.learningmaterial.lesson;

import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("SELECT t FROM Lesson t WHERE lower(t.name) LIKE lower(concat('%', :#{#lessonSearchDto.name}, '%'))")
    Page<Lesson> searchLessons(LessonSearchDto lessonSearchDto, Pageable pageable);

    boolean existsByName(String name);

    List<Lesson> findAllByIdIn(List<Integer> ids);
}
