package com.fluentenglish.web.learning.material.lesson.admin;

import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonSearchDto;
import com.fluentenglish.web.learning.material.lesson.admin.dto.LessonCreateUpdateDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonDto;
import com.fluentenglish.web.learning.material.word.admin.WordService;
import com.fluentenglish.web.learning.material.word.dto.WordDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController("AdminLessonController")
@RequestMapping("/admin/api/lessons")
public class LessonController {
    private final LessonService lessonService;

    private final WordService wordService;
    public LessonController(LessonService lessonService, WordService wordService) {
        this.lessonService = lessonService;
        this.wordService = wordService;
    }

    @GetMapping
    public ResponseEntity<PageDto<LessonDto>> index(@RequestParam(name = "q", required = false, defaultValue = "") String lessonSearch,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") int page
    ) {
        LessonSearchDto lessonSearchDto = new LessonSearchDto();
        lessonSearchDto.setName(lessonSearch.trim());
        PageDto<LessonDto> lessonsPage = lessonSearchDto.getName().isEmpty()
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

    @GetMapping("/{id}/words")
    public ResponseEntity<List<WordDto>> getWords(@PathVariable int id) {
        List<WordDto> words = wordService.getWordsByLessonId(id);

        return ResponseEntity.ok(words);
    }
}
