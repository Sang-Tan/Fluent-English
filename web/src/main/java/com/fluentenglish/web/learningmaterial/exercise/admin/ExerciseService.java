package com.fluentenglish.web.learningmaterial.exercise.admin;

import com.fluentenglish.web.learningmaterial.exercise.admin.request.ExerciseCreateDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.request.ExerciseUpdateDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.response.ExerciseDto;

public interface ExerciseService {
    void createExercise(ExerciseCreateDto exerciseCreateDto);

    ExerciseDto getExerciseById(int exerciseId);

    void updateExercise(int exerciseId, ExerciseUpdateDto exerciseCreateDto);

    void setExercisePublicity(int exerciseId, boolean isPublic);

    void deleteExercise(int exerciseId);
}
