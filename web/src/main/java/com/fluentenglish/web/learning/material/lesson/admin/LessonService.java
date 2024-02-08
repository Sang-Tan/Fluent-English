package com.fluentenglish.web.learning.material.lesson.admin;

import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonCreateUpdateDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonSearchDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonDto;

public interface LessonService {
    void createLesson(LessonCreateUpdateDto lessonDto);

    LessonDto getLessonById(int id);

    PageDto getAllLessons(int page);

    PageDto searchLessons(LessonSearchDto lessonSearchDto, int page);

    void updateLesson(int id, LessonCreateUpdateDto lessonDto);

    void deleteLesson(int id);
}
