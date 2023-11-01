package com.fluentenglish.web.learningmaterial.lesson.admin.mapper;

import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControllerLessonMapper {
    LessonCreateDto toLessonCreateUpdateDto(LessonDto lessonDto);
}
