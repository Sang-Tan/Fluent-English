package com.fluentenglish.web.learningmaterial.lesson.admin.mapper;

import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceLessonMapper {
    @Mapping(target = "topicId", source = "topic.id")
    LessonDto toLessonDto(Lesson lesson);

    Lesson toLesson(LessonDto lessonDto);

    Lesson toLesson(LessonCreateDto lessonCreateDto);

    void updateLesson(LessonUpdateDto lessonUpdateDto, @MappingTarget Lesson lesson);
}
