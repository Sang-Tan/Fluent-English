package com.fluentenglish.web.learningmaterial.topic.admin;

import com.fluentenglish.web.common.exception.errorresponse.BadRequestException;
import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.common.viewattribute.DataTable;
import com.fluentenglish.web.common.viewattribute.DataTableRow;
import com.fluentenglish.web.common.viewattribute.UserInfo;
import com.fluentenglish.web.learningmaterial.topic.admin.mapper.ControllerTopicMapper;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicSearchDto;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/topics")
public class TopicAPIController {
    private final TopicService topicService;

    private final ControllerTopicMapper topicMapper;

    public TopicAPIController(TopicService topicService, ControllerTopicMapper topicMapper) {
        this.topicService = topicService;
        this.topicMapper = topicMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        try{
            List<TopicDto> topics = topicService.getAllTopics();
            List<DataTableRow> rows = topics.stream().map(topic -> DataTableRow.builder()
                    .id(topic.getId())
                    .name(topic.getName())
                    .updateUrl("/admin/topic/" + topic.getId())
                    .deleteUrl("/admin/topic/" + topic.getId() + "/delete")
                    .build()).toList();

            DataTable dataTable = DataTable.builder()
                    .isArrangeable(false)
                    .isPaginable(true)
                    .changePositionsUrl("/admin/topic/change-positions")
                    .rows(rows)
                    .build();

            Map<String, Object> response = Map.of(
                    "topicDataTable", dataTable,
                    "userInfo", getUserInfo(),
                    "title", "Danh sách chủ đề"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BadRequestException("Cannot get topics");
        }
    }

    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> getCreateTopicPage() {
        return ResponseEntity.ok(Map.of(
                "userInfo", getUserInfo(),
                "title", "Tạo chủ đề",
                "redirectUrl", "/learningmaterial/topic/create"
        ));
    }

    @GetMapping("/search-json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchTopic(TopicSearchDto topicSearchDto) {
        try {
            return ResponseEntity.ok(Map.of("topics", topicService.searchTopics(topicSearchDto)));
        } catch (Exception e) {
            throw new BadRequestException("Cannot search topics");
        }
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createTopic(@RequestBody @Valid TopicCreateUpdateDto topicDto,
                                    Errors errors) {
        try {
            validateUserInput(errors);
            topicService.createTopic(topicDto);
        } catch (UserInputException e) {
            Map<String, Object> response = Map.of(
                    "message", "Cannot create topic",
                    "data", Map.of(
                            "submittedTopic", topicDto,
                            "userInputErrors", e
                    )
            );
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.created(URI.create("/admin/topic"))
                .body(Map.of(
                        "message", "Create topic successfully",
                        "data", topicDto
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTopicDetailPage(@PathVariable int id, @RequestBody(required = false) TopicCreateUpdateDto submittedTopic) {
        try{
            TopicDto topic = topicService.getTopicById(id);

            if (submittedTopic != null) {
                topicMapper.updateTopicDtoFromCreateUpdateDto(submittedTopic, topic);
            }

            return ResponseEntity.ok(Map.of(
                    "topic", topic,
                    "userInfo", getUserInfo(),
                    "title", "Chi tiết chủ đề"
            ));
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot find topic with id " + id);
        } catch (Exception e) {
            throw new BadRequestException("Cannot get topic");
        }
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Map<String, Object>> updateTopic(@PathVariable int id,
                                    @RequestBody @Valid TopicCreateUpdateDto topicDto,
                                    Errors errors) {
        try {
            validateUserInput(errors);
            topicService.updateTopic(id, topicDto);
        } catch (UserInputException e) {
            Map<String, Object> response = Map.of(
                    "message", "Cannot update topic",
                    "data", Map.of(
                            "submittedTopic", topicDto,
                            "userInputErrors", e
                    )
            );
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(
                Map.of(
                        "message", "Update topic successfully",
                        "data", topicService.getTopicById(id)
                ));
    }

    @PostMapping("/{id}/set-publicity")
    public ResponseEntity<?> setPublicity(@PathVariable int id,
                               @RequestParam("is-public") boolean isPublic) {
        try {
            topicService.setTopicPublicity(id, isPublic);
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot find topic with id " + id);
        } catch (Exception e) {
            throw new BadRequestException("Cannot set publicity");
        }

        return ResponseEntity.ok(Map.of(
                "message", "Set publicity successfully",
                "data", topicService.getTopicById(id)
        ));
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> deleteTopic(@PathVariable int id) {
        try {
            topicService.deleteTopic(id);
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot find topic with id " + id);
        } catch (Exception e) {
            throw new BadRequestException("Cannot delete topic");
        }

        return ResponseEntity.ok(Map.of("message", "Delete topic successfully"));
    }

    private UserInfo getUserInfo() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return UserInfo.builder()
                .username(authentication.getName())
                .build();
    }

    private void validateUserInput(Errors errors) {
        List<InputErrorInfo> errorInfos = errors.getFieldErrors().stream()
                .map(error -> new InputErrorInfo(error.getField(), error.getDefaultMessage()))
                .toList();

        if (!errorInfos.isEmpty()) {
            throw new UserInputException(errorInfos);
        }
    }
}
