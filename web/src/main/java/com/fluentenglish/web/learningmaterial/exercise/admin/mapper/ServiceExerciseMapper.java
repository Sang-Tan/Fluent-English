package com.fluentenglish.web.learningmaterial.exercise.admin.mapper;

import com.fluentenglish.web.learningmaterial.exercise.Exercise;
import com.fluentenglish.web.learningmaterial.exercise.admin.request.ExerciseCreateDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.request.ExerciseUpdateDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.response.ExerciseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceExerciseMapper {
    @Mapping(target = "lessonId", source = "lesson.id")
    ExerciseDto toExerciseDto(Exercise exercise);

    Exercise toExercise(ExerciseCreateDto exerciseCreateDto);

    void updateExercise(ExerciseUpdateDto exerciseUpdateDto, @MappingTarget Exercise exercise);
}
