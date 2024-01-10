package com.fluentenglish.web.learningmaterial.exercise.admin.mapper;

import com.fluentenglish.web.learningmaterial.exercise.admin.request.ExerciseCreateDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.response.ExerciseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControllerExerciseMapper {
    ExerciseCreateDto toExerciseCreateUpdateDto(ExerciseDto exerciseDto);
}
