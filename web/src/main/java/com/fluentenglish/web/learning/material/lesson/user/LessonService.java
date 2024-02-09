package com.fluentenglish.web.learning.material.lesson.user;

import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonSearchDto;

public interface LessonService {
    LessonDto getLessonById(int id);

    PageDto getAllLessons(int page);

    PageDto searchLessons(LessonSearchDto lessonSearchDto, int page);
}
