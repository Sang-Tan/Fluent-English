package com.fluentenglish.web.learningmaterial.lesson.admin;

import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;

import java.util.List;
import java.util.Map;

public interface LessonService {
    void createLesson(LessonCreateUpdateDto lessonDto);

    LessonDto getLessonById(int id);

    boolean lessonExistsById(int id);

    List<LessonDto> getAllLessons();

    List<LessonDto> searchLessons(LessonSearchDto lessonSearchDto);

    void updateLesson(int id, LessonCreateUpdateDto lessonDto);

    void changeExercisePosition(Map<Integer, Integer> newPositionMap);

    void setLessonPublicity(int id, boolean isPublic);

    void deleteLesson(int id);
}
