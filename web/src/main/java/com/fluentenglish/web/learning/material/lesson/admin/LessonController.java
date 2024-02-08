package com.fluentenglish.web.learning.material.lesson.admin;

import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonSearchDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonCreateUpdateDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController("AdminLessonController")
@RequestMapping("/admin/api/lessons")
public class LessonController {
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable int id) {
        lessonService.deleteLesson(id);

        return ResponseEntity.noContent().build();
    }
}
