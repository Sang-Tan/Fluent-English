package com.fluentenglish.web.learningmaterial.lesson.admin;

import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin/api/lessons")
public class LessonController {
    private final LessonService lessonService;
    
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<LessonDto>> index(@RequestParam(name = "q", required = false, defaultValue = "") String lessonSearch) {
        LessonSearchDto lessonSearchDto = new LessonSearchDto();
        lessonSearchDto.setName(lessonSearch.trim());
        List<LessonDto> lessons = lessonSearchDto.getName().isEmpty()
                ? lessonService.getAllLessons()
                : lessonService.searchLessons(lessonSearchDto);

        return ResponseEntity.ok(lessons);
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

    @PutMapping("/{id}/set-publicity")
    public ResponseEntity<Void> setPublicity(@PathVariable int id,
                               @RequestParam("is-public") boolean isPublic) {
        lessonService.setLessonPublicity(id, isPublic);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable int id) {
        lessonService.deleteLesson(id);

        return ResponseEntity.noContent().build();
    }
}
