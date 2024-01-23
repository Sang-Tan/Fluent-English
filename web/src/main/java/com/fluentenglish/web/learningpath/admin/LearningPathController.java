package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.common.request.PublicityRequest;
import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.lesson.admin.mapper.ServiceLessonMapper;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import com.fluentenglish.web.learningpath.LearningPath;
import com.fluentenglish.web.learningpath.admin.request.AddLessonToLearningPathDto;
import com.fluentenglish.web.learningpath.admin.request.LearningPathCreateDto;
import com.fluentenglish.web.learningpath.admin.request.LearningPathUpdateDto;
import com.fluentenglish.web.learningpath.admin.response.LearningPathDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/learning-paths")
public class LearningPathController {
    private final LearningPathServiceImpl learningPathService;
    private final LearningPathMapper learningPathMapper;
    private final ServiceLessonMapper serviceLessonMapper;
    public LearningPathController(LearningPathServiceImpl learningPathService,
                                  LearningPathMapper learningPathMapper,
                                  ServiceLessonMapper serviceLessonMapper) {
        this.learningPathService = learningPathService;
        this.learningPathMapper = learningPathMapper;
        this.serviceLessonMapper = serviceLessonMapper;
    }

    @GetMapping(value = "/{learningPathId}")
    public ResponseEntity<LearningPathDto> getLearningPath(@PathVariable("learningPathId") Integer learningPathId)
    {
        LearningPath learningPath = learningPathService.getLearningPath(learningPathId);

        return ResponseEntity.ok(learningPathMapper.ToLearningPathDto(learningPath));
    }

    @GetMapping
    public ResponseEntity<List<LearningPathDto>> getAllLearningPaths() {

        List<LearningPath> learningPaths = learningPathService.getAllLearningPath();
        List<LearningPathDto> learningPathDtos = learningPaths.stream()
                .map(learningPathMapper::ToLearningPathDto)
                .toList();

        return ResponseEntity.ok(learningPathDtos);
    }

    @PostMapping
    public ResponseEntity<Map<String, Integer>> createLearningPath(@RequestBody @Valid LearningPathCreateDto form) {
        LearningPath learningPathSaved = learningPathService.createLearningPath(form);

        Map<String, Integer> response = new HashMap<>();
        response.put("learning-path-id", learningPathSaved.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{learningPathId}")
    public ResponseEntity<Void> updateLearningPath(@PathVariable("learningPathId") Integer learningPathId,
                                     @RequestBody @Valid LearningPathUpdateDto form) {
        learningPathService.updateLearningPath(learningPathId, form);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{learning-path-id}/publicity")
    public ResponseEntity<Void> setPublicity(@PathVariable("learning-path-id") Integer learningPathId,
                                             @RequestBody PublicityRequest isPublic) {
        learningPathService.setLearningPathPublicity(learningPathId,isPublic.getIsPublic());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{learningPathId}")
    public ResponseEntity<Void> deleteLearningPath(@PathVariable("learningPathId") Integer learningPathId) {
        learningPathService.deleteLearningPath(learningPathId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{learningPathId}/lessons")
    public ResponseEntity<List<LessonDto>> getLessonsByLearningPathId(
            @PathVariable("learningPathId") Integer learningPathId) {
        List<Lesson> lessons = learningPathService.getLessonsByLearningPathId(learningPathId);
        List<LessonDto> lessonDtos = lessons.stream().map(serviceLessonMapper::lessonToLessonDto)
                .toList();
        return ResponseEntity.ok(lessonDtos);
    }
    @PostMapping("/{learningPathId}/lessons")
    public ResponseEntity<Void> addLessonToLearningPath(
            @PathVariable("learningPathId") Integer learningPathId,
            @   RequestBody AddLessonToLearningPathDto lessonDto) {
        learningPathService.addLesson(learningPathId, lessonDto.getLessonId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{learningPathId}/lessons/{lessonId}")
    public ResponseEntity<Void> removeLessonFromLearningPath(
            @PathVariable("learningPathId") Integer learningPathId,
            @PathVariable("lessonId") Integer lessonId) {
        learningPathService.removeLesson(learningPathId, lessonId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/{learningPathId}/unassigned-lessons")
    public ResponseEntity<PageDto> getUnassignedLessonsByLearningPathId(
            @PathVariable("learningPathId") Integer learningPathId,
            @RequestParam(value = "q", required = false, defaultValue = "") String search,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        LessonSearchDto lessonSearchDto = new LessonSearchDto();
        lessonSearchDto.setName(search.trim());
        PageDto lessonsPage = lessonSearchDto.getName().isEmpty()
                ? learningPathService.getUnassignedLessons(learningPathId, page)
                : learningPathService.searchUnassignedLessons(lessonSearchDto, learningPathId, page);
        return ResponseEntity.ok(lessonsPage);
    }
}
