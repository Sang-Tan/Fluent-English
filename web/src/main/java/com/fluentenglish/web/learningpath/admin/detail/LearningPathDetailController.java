package com.fluentenglish.web.learningpath.admin.detail;

import com.fluentenglish.web.learningmaterial.topic.Topic;
import com.fluentenglish.web.learningmaterial.topic.admin.mapper.ServiceTopicMapper;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/api/learning-path")
@Slf4j
public class LearningPathDetailController {
    private final LearningPathDetailService learningPathDetailService;
    private final ServiceTopicMapper serviceTopicMapper;
    public LearningPathDetailController(LearningPathDetailService learningPathDetailService,
                                        ServiceTopicMapper serviceTopicMapper) {
        this.learningPathDetailService = learningPathDetailService;
        this.serviceTopicMapper = serviceTopicMapper;
    }
    @GetMapping(value = "/{learningPathId}/lessons")
    public ResponseEntity<List<TopicDto>> getLessonsByLearningPathId(
            @PathVariable("learningPathId") Integer learningPathId) {
        List<Topic> topics = learningPathDetailService.getTopicsByLearningPathId(learningPathId);
        List<TopicDto> topicDtos = topics.stream().map(serviceTopicMapper::topicToTopicDto)
                .toList();
        return ResponseEntity.ok(topicDtos);
    }

    @PutMapping("/{learningPathId}/lessons")
    public ResponseEntity<Void> setLessonsForLearningPathId(
            @PathVariable("learningPathId") Integer learningPathId
            ,@RequestParam("lesson-ids") String lessons) {
        List<Integer> lessonIds = Arrays.stream(lessons.split(",")).map(Integer::parseInt).toList();
        List<LearningPathDetail> result = learningPathDetailService
                .setTopicsByLearningPathId(learningPathId, lessonIds);
        log.debug(result.stream().map(LearningPathDetail::toString)
                .collect(Collectors.joining(",")) + " added successfully");

        return ResponseEntity.noContent().build();
    }
}
