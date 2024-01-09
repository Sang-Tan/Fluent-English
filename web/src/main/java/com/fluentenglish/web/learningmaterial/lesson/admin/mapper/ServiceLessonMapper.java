package com.fluentenglish.web.learningmaterial.lesson.admin.mapper;

import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceLessonMapper {

    Lesson lessonCreateUpdateDtoToLesson(LessonCreateUpdateDto lessonCreateUpdateDto);

    LessonDto lessonToLessonDto(Lesson lesson);

    void updateLessonFromDto(LessonCreateUpdateDto lessonCreateUpdateDto, @MappingTarget Lesson lesson);
}
