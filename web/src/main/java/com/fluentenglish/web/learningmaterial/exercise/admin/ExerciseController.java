package com.fluentenglish.web.learningmaterial.exercise.admin;

import com.fluentenglish.web.learningmaterial.exercise.admin.request.ExerciseCreateDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.request.ExerciseUpdateDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.response.ExerciseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/admin/api/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<Void> createExercise(@RequestBody @Valid ExerciseCreateDto exerciseCreateDto) {
        exerciseService.createExercise(exerciseCreateDto);

        return ResponseEntity.created(URI.create("/admin/lesson/" + exerciseCreateDto.getLessonId())).build();
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> getExercise(@PathVariable int exerciseId) {
        ExerciseDto exercise = exerciseService.getExerciseById(exerciseId);

        return ResponseEntity.ok(exercise);
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<Void> updateExercise(@PathVariable int exerciseId,
                                     @RequestBody @Valid ExerciseUpdateDto exerciseUpdateDto) {
        exerciseService.updateExercise(exerciseId, exerciseUpdateDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{exerciseId}/set-publicity")
    public ResponseEntity<Void> setExercisePublicity(@PathVariable int exerciseId,
                                           @RequestParam boolean isPublic) {
        exerciseService.setExercisePublicity(exerciseId, isPublic);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable int exerciseId) {
        exerciseService.deleteExercise(exerciseId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{exerciseId}/update-introduction")
    public ResponseEntity<Void> updateExerciseIntroduction(@PathVariable int exerciseId,
                                                 @RequestParam String content) {
        exerciseService.updateExerciseIntroduction(exerciseId, content);

        return ResponseEntity.noContent().build();
    }
}
