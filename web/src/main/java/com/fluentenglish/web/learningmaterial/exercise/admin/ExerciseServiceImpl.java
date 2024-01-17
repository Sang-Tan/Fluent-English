package com.fluentenglish.web.learningmaterial.exercise.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.learningmaterial.exercise.Exercise;
import com.fluentenglish.web.learningmaterial.exercise.ExerciseRepository;
import com.fluentenglish.web.learningmaterial.exercise.admin.mapper.ServiceExerciseMapper;
import com.fluentenglish.web.learningmaterial.exercise.admin.request.ExerciseCreateDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.request.ExerciseUpdateDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.response.ExerciseDto;
import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final ServiceExerciseMapper exerciseMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ServiceExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    public void createExercise(ExerciseCreateDto exerciseCreateDto) {
        Exercise exercise = exerciseMapper.toExercise(exerciseCreateDto);

        exercise.setLesson(Lesson.builder().id(exerciseCreateDto.getLessonId()).build());

        int maxPosition = exerciseRepository
                .getMaxPositionOfExercisesInLesson(exerciseCreateDto.getLessonId())
                .orElse(0);
        exercise.setPosition(maxPosition + 1);

        exerciseRepository.save(exercise);
    }

    @Override
    public ExerciseDto getExerciseById(int exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new NotFoundException("Exercise not found"));

        return exerciseMapper.toExerciseDto(exercise);
    }

    @Override
    public void updateExercise(int exerciseId, ExerciseUpdateDto exerciseUpdateDto) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new NotFoundException("Exercise not found"));
        exerciseMapper.updateExercise(exerciseUpdateDto, exercise);
        exerciseRepository.save(exercise);
    }

    @Override
    public void setExercisePublicity(int exerciseId, boolean isPublic) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new NotFoundException("Exercise not found"));
        exercise.setPublic(isPublic);
        exerciseRepository.save(exercise);
    }

    @Override
    public void deleteExercise(int exerciseId) {
        if(!exerciseRepository.existsById(exerciseId)) {
            throw new NotFoundException("Exercise not found");
        }
        exerciseRepository.deleteById(exerciseId);
    }
}
