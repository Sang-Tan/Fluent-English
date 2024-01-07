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
    @GetMapping(value = "/{learningPathId}/topics")
    public ResponseEntity<List<TopicDto>> getTopicsByLearningPathId(
            @PathVariable("learningPathId") Integer learningPathId) {
        List<Topic> topics = learningPathDetailService.getTopicsByLearningPathId(learningPathId);
        List<TopicDto> topicDtos = topics.stream().map(serviceTopicMapper::topicToTopicDto)
                .toList();
        return ResponseEntity.ok(topicDtos);
    }

    @PostMapping("/{learningPathId}/topics")
    public ResponseEntity<Void> setTopicsByLearningPathId(
            @PathVariable("learningPathId") Integer learningPathId
            ,@RequestParam("topic-ids") String topics) {
        List<Integer> topicIds = Arrays.stream(topics.split(",")).map(Integer::parseInt).toList();
        List<LearningPathDetail> result = learningPathDetailService
                .setTopicsByLearningPathId(learningPathId, topicIds);
        log.debug(result.stream().map(LearningPathDetail::toString)
                .collect(Collectors.joining(",")) + " added successfully");

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{learningPathId}/add-topic/{topicId}")
    public ResponseEntity<Void> addTopicToLearningPath(
            @PathVariable("learningPathId") Integer learningPathId,
            @PathVariable("topicId") Integer topicId
    ) {
        LearningPathDetail result = learningPathDetailService
                .addTopicToLearningPath(learningPathId, topicId);
        log.debug(result + " added successfully");

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{learningPathId}/remove-topic/{topicId}")
    public ResponseEntity<Void> removeTopicFromLearningPath(
            @PathVariable("learningPathId") Integer learningPathId,
            @PathVariable("topicId") Integer topicId
    ) {
        learningPathDetailService.removeTopicFromLearningPath(learningPathId, topicId);
        return ResponseEntity.noContent().build();
    }
}
