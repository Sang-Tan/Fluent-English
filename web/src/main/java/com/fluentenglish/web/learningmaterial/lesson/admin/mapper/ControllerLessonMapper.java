package com.fluentenglish.web.learningmaterial.lesson.admin.mapper;

import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ControllerLessonMapper {
    void updateLessonDtoFromCreateUpdateDto(LessonCreateUpdateDto lessonCreateUpdateDto, @MappingTarget LessonDto lessonDto);
}
