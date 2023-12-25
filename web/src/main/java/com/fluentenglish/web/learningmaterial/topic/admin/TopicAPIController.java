package com.fluentenglish.web.learningmaterial.topic.admin;

import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicSearchDto;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin/api/topics")
public class TopicAPIController {
    private final TopicService topicService;
    
    public TopicAPIController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> index(@RequestParam(name = "q", required = false, defaultValue = "") String topicSearch) {
        TopicSearchDto topicSearchDto = new TopicSearchDto();
        topicSearchDto.setName(topicSearch.trim());
        List<TopicDto> topics = topicSearchDto.getName().isEmpty()
                ? topicService.getAllTopics()
                : topicService.searchTopics(topicSearchDto);

        return ResponseEntity.ok(topics);
    }

    @PostMapping
    public ResponseEntity<Void> createTopic(@RequestBody @Valid TopicCreateUpdateDto topicDto) {
        topicService.createTopic(topicDto);

        return ResponseEntity.created(URI.create("/admin/topic")).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> topicDetail(@PathVariable int id) {
        TopicDto topic = topicService.getTopicById(id);

        return ResponseEntity.ok(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTopic(@PathVariable int id,
                                    @RequestBody @Valid TopicCreateUpdateDto topicDto) {
        topicService.updateTopic(id, topicDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/set-publicity")
    public ResponseEntity<Void> setPublicity(@PathVariable int id,
                               @RequestParam("is-public") boolean isPublic) {
        topicService.setTopicPublicity(id, isPublic);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable int id) {
        topicService.deleteTopic(id);

        return ResponseEntity.noContent().build();
    }
}
