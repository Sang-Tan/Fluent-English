package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.learningmaterial.topic.Topic;
import com.fluentenglish.web.learningmaterial.topic.admin.mapper.ServiceTopicMapper;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import com.fluentenglish.web.learningpath.LearningPath;
import com.fluentenglish.web.learningpath.admin.detail.LearningPathDetail;
import com.fluentenglish.web.learningpath.admin.request.LearningPathCreateDto;
import com.fluentenglish.web.learningpath.admin.request.LearningPathUpdateDto;
import com.fluentenglish.web.learningpath.admin.response.LearningPathDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/api/learning-path")
@Slf4j
public class LearningPathController {
    private final LearningPathService learningPathService;
    private final LearningPathMapper learningPathMapper;
    private final ServiceTopicMapper serviceTopicMapper;
    public LearningPathController(LearningPathService learningPathService,
                                  LearningPathMapper learningPathMapper,
                                  ServiceTopicMapper serviceTopicMapper) {
        this.learningPathService = learningPathService;
        this.learningPathMapper = learningPathMapper;
        this.serviceTopicMapper = serviceTopicMapper;
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
        log.debug(learningPathSaved + " created successfully");

        Map<String, Integer> response = new HashMap<>();
        response.put("learning-path-id", learningPathSaved.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{learningPathId}")
    public ResponseEntity<Void> updateLearningPath(@PathVariable("learningPathId") Integer learningPathId,
                                     @RequestBody @Valid LearningPathUpdateDto form) {
        LearningPath learningPathSaved = learningPathService.updateLearningPath(learningPathId, form);
        log.debug(learningPathSaved + " updated successfully");

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{learning-path-id}/publicity")
    public ResponseEntity<Void> setPublicity(@PathVariable("learning-path-id") Integer learningPathId,
                                             @RequestParam("is-public") boolean isPublic) {
        LearningPath saved = learningPathService.setLearningPathPublicity(learningPathId, isPublic);
        log.debug("set learning path publicity -> " + saved);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{learningPathId}")
    public ResponseEntity<Void> deleteLearningPath(@PathVariable("learningPathId") Integer learningPathId) {
        learningPathService.deleteLearningPath(learningPathId);
        log.debug("delete learning path -> " + learningPathId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{learningPathId}/lessons")
    public ResponseEntity<List<TopicDto>> getLessonsByLearningPathId(
            @PathVariable("learningPathId") Integer learningPathId) {
        List<Topic> topics = learningPathService.getTopicsByLearningPathId(learningPathId);
        List<TopicDto> topicDtos = topics.stream().map(serviceTopicMapper::topicToTopicDto)
                .toList();
        return ResponseEntity.ok(topicDtos);
    }

    @PutMapping("/{learningPathId}/lessons")
    public ResponseEntity<Void> setLessonsForLearningPathId(
            @PathVariable("learningPathId") Integer learningPathId
            ,@RequestParam("lesson-ids") String lessons) {
        List<Integer> lessonIds = Arrays.stream(lessons.split(",")).map(Integer::parseInt).toList();
        List<LearningPathDetail> result = learningPathService
                .setTopicsByLearningPathId(learningPathId, lessonIds);
        log.debug(result.stream().map(LearningPathDetail::toString)
                .collect(Collectors.joining(",")) + " added successfully");

        return ResponseEntity.noContent().build();
    }
}
