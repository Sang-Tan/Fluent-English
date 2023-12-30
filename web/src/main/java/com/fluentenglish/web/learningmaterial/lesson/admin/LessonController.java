package com.fluentenglish.web.learningmaterial.lesson.admin;

import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/admin/api/lessons")
public class LessonController {
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ResponseEntity<Void> createLesson(@RequestBody @Valid LessonCreateDto lessonCreateDto) {
        lessonService.createLesson(lessonCreateDto);

        return ResponseEntity.created(URI.create("/admin/topic/" + lessonCreateDto.getTopicId())).build();
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonDto> getLesson(@PathVariable int lessonId) {
        LessonDto lesson = lessonService.getLessonById(lessonId);

        return ResponseEntity.ok(lesson);
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<Void> updateLesson(@PathVariable int lessonId,
                                     @RequestBody @Valid LessonUpdateDto lessonUpdateDto) {
        lessonService.updateLesson(lessonId, lessonUpdateDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{lessonId}/set-publicity")
    public ResponseEntity<Void> setLessonPublicity(@PathVariable int lessonId,
                                           @RequestParam boolean isPublic) {
        lessonService.setLessonPublicity(lessonId, isPublic);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable int lessonId) {
        lessonService.deleteLesson(lessonId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{lessonId}/update-introduction")
    public ResponseEntity<Void> updateLessonIntroduction(@PathVariable int lessonId,
                                                 @RequestParam String content) {
        lessonService.updateLessonIntroduction(lessonId, content);

        return ResponseEntity.noContent().build();
    }
}
