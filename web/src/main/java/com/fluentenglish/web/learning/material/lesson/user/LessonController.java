package com.fluentenglish.web.learning.material.lesson.user;

import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonSearchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("UserLessonController")
@RequestMapping("/user/api/lessons")
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

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> lessonDetail(@PathVariable int id) {
        LessonDto lesson = lessonService.getLessonById(id);

        return ResponseEntity.ok(lesson);
    }
}
