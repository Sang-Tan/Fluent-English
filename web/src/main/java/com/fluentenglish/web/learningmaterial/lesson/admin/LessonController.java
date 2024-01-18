package com.fluentenglish.web.learningmaterial.lesson.admin;

import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learningmaterial.exercise.admin.ExerciseService;
import com.fluentenglish.web.learningmaterial.exercise.admin.response.ExerciseDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin/api/lessons")
public class LessonController {
    private final LessonService lessonService;

    private final ExerciseService exerciseService;
    public LessonController(LessonService lessonService, ExerciseService exerciseService) {
        this.lessonService = lessonService;
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<PageDto> index(@RequestParam(name = "q", required = false, defaultValue = "") String lessonSearch,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") int page
    ) {
        LessonSearchDto lessonSearchDto = new LessonSearchDto();
        lessonSearchDto.setName(lessonSearch.trim());
        PageDto lessonsPage = lessonSearchDto.getName().isEmpty()
                ? lessonService.getAllLessons(page)
                : lessonService.searchLessons(lessonSearchDto, page);

        return ResponseEntity.ok(lessonsPage);
    }

    @PostMapping
    public ResponseEntity<Void> createLesson(@RequestBody @Valid LessonCreateUpdateDto lessonDto) {
        lessonService.createLesson(lessonDto);

        return ResponseEntity.created(URI.create("/admin/lesson")).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> lessonDetail(@PathVariable int id) {
        LessonDto lesson = lessonService.getLessonById(id);

        return ResponseEntity.ok(lesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLesson(@PathVariable int id,
                                             @RequestBody @Valid LessonCreateUpdateDto lessonDto) {
        lessonService.updateLesson(id, lessonDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/publicity")
    public ResponseEntity<Void> setPublicity(@PathVariable int id,
                                             @RequestBody boolean isPublic) {
        lessonService.setLessonPublicity(id, isPublic);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable int id) {
        lessonService.deleteLesson(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exercises")
    public ResponseEntity<List<ExerciseDto>> getExercises(@PathVariable int id) {
        List<ExerciseDto> exercises = exerciseService.getExercisesByLessonId(id);

        return ResponseEntity.ok(exercises);
    }
}
