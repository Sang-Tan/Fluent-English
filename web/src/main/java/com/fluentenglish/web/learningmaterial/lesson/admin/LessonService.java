package com.fluentenglish.web.learningmaterial.lesson.admin;

import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;

public interface LessonService {
    void createLesson(LessonCreateDto lessonCreateDto);

    LessonDto getLessonById(int lessonId);

    int getTopicIdOfLesson(int lessonId);

    void updateLesson(int lessonId, LessonUpdateDto lessonCreateDto);

    void setLessonPublicity(int lessonId, boolean isPublic);

    void deleteLesson(int lessonId);

    String getLessonIntroduction(int lessonId);

    void updateLessonIntroduction(int lessonId, String content);
}
