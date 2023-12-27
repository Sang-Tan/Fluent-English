package com.fluentenglish.web.learningmaterial.topic.admin;

import com.fluentenglish.web.common.exception.errorresponse.BadRequestException;
import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicSearchDto;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/topics")
public class TopicAPIController {
    private final TopicService topicService;

    public TopicAPIController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> index(@RequestParam(name = "q", required = false, defaultValue = "") String topicSearch) {
        TopicSearchDto topicSearchDto = new TopicSearchDto();
        topicSearchDto.setName(topicSearch.trim());
        List<TopicDto> topics = topicSearchDto.getName().isEmpty()
                ? topicService.getAllTopics()
                : topicService.searchTopics(topicSearchDto);

        return ResponseEntity.ok(Map.of(
                "data", Map.of(
                        "topics", topics
                )
        ));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTopic(@RequestBody @Valid TopicCreateUpdateDto topicDto) {
        try {
            topicService.createTopic(topicDto);
        } catch (Exception e) {
            Map<String, Object> response = Map.of(
                    "message", "Cannot create topic",
                    "data", Map.of(
                            "submittedTopic", topicDto,
                            "userInputErrors", e
                    )
            );
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.created(URI.create("/admin/topic"))
                .body(Map.of(
                        "data", topicDto
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTopicDetailPage(@PathVariable int id) {
        try{
            TopicDto topic = topicService.getTopicById(id);

            return ResponseEntity.ok(Map.of(
                    "data", topic
            ));
        } catch(NotFoundException e) {
            throw new NotFoundException("Cannot get topic with id " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTopic(@PathVariable int id,
                                    @RequestBody @Valid TopicCreateUpdateDto topicDto) {
        try {
            topicService.updateTopic(id, topicDto);
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot find topic with id " + id);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot update topic");
        }

        return ResponseEntity.ok(
                Map.of(
                        "data", topicService.getTopicById(id)
                ));
    }

    @PutMapping("/{id}/set-publicity")
    public ResponseEntity<?> setPublicity(@PathVariable int id,
                               @RequestParam("is-public") boolean isPublic) {
        try {
            topicService.setTopicPublicity(id, isPublic);
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot find topic with id " + id);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot set publicity");
        }

        return ResponseEntity.ok(Map.of(
                "data", topicService.getTopicById(id)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable int id) {
        try {
            topicService.deleteTopic(id);
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot find topic with id " + id);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot delete topic");
        }

        return ResponseEntity.ok(Map.of("message", "Delete topic successfully"));
    }
}
