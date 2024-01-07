package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.learningpath.LearningPath;
import com.fluentenglish.web.learningpath.admin.request.LearningPathCreateDto;
import com.fluentenglish.web.learningpath.admin.request.LearningPathUpdateDto;
import com.fluentenglish.web.learningpath.admin.response.LearningPathDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/learning-path")
@Slf4j
public class LearningPathController {
    private final LearningPathService learningPathService;
    private final LearningPathMapper learningPathMapper;
    public LearningPathController(LearningPathService learningPathService,
                                  LearningPathMapper learningPathMapper) {
        this.learningPathService = learningPathService;
        this.learningPathMapper = learningPathMapper;
    }

    @GetMapping(value = "/{learningPathId}")
    public ResponseEntity<LearningPathDto> getLearningPath(@PathVariable("learningPathId") Integer learningPathId)
    {
        LearningPath learningPath = learningPathService.getLearningPath(learningPathId);

        return ResponseEntity.ok(learningPathMapper.learningPathToLearningPathDto(learningPath));
    }

    @GetMapping
    public ResponseEntity<List<LearningPathDto>> getAllLearningPaths() {

        List<LearningPath> learningPaths = learningPathService.getAllLearningPath();
        List<LearningPathDto> learningPathDtos = learningPaths.stream()
                .map(learningPathMapper::learningPathToLearningPathDto)
                .toList();

        return ResponseEntity.ok(learningPathDtos);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Integer>> createLearningPath(@RequestBody @Valid LearningPathCreateDto form) {
        LearningPath learningPathSaved = learningPathService.createLearningPath(form);
        log.debug(learningPathSaved + " created successfully");

        Map<String, Integer> response = new HashMap<>();
        response.put("learning-path-id", learningPathSaved.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{learningPathId}/update")
    public ResponseEntity<Void> updateLearningPath(@PathVariable("learningPathId") Integer learningPathId,
                                     @RequestBody @Valid LearningPathUpdateDto form) {
        LearningPath learningPathSaved = learningPathService.updateLearningPath(learningPathId, form);
        log.debug(learningPathSaved + " updated successfully");

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{learningPathId}/delete")
    public ResponseEntity<Void> deleteLearningPath(@PathVariable("learningPathId") Integer learningPathId) {
        learningPathService.deleteLearningPath(learningPathId);
        log.debug("delete learning path -> " + learningPathId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{learning-path-id}/set-publicity")
    public ResponseEntity<Void> setPublicity(@PathVariable("learning-path-id") Integer learningPathId,
                               @RequestParam("is-public") boolean isPublic) {
        LearningPath saved = learningPathService.setLearningPathPublicity(learningPathId, isPublic);
        log.debug("set learning path publicity -> " + saved);

        return ResponseEntity.noContent().build();
    }

}
