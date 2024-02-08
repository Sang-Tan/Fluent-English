package com.fluentenglish.web.learning.material.lesson;

import com.fluentenglish.web.learning.material.lesson.dto.LessonCreateUpdateDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceLessonMapper {
    Lesson lessonCreateUpdateDtoToLesson(LessonCreateUpdateDto lessonCreateUpdateDto);

    LessonDto lessonToLessonDto(Lesson lesson);

    void updateLessonFromDto(LessonCreateUpdateDto lessonCreateUpdateDto, @MappingTarget Lesson lesson);
}
