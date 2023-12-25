package com.fluentenglish.web.learningmaterial.lesson.admin;

import com.fluentenglish.web.common.exception.errorresponse.BadRequestException;
import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.common.viewattribute.UserInfo;
import com.fluentenglish.web.learningmaterial.lesson.admin.mapper.ControllerLessonMapper;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import com.fluentenglish.web.learningmaterial.topic.admin.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/lessons")
public class LessonAPIController {
    private final TopicService topicService;

    private final LessonService lessonService;

    private final ControllerLessonMapper lessonMapper;

    public LessonAPIController(TopicService topicService,
                               LessonService lessonService,
                               ControllerLessonMapper lessonMapper) {
        this.topicService = topicService;
        this.lessonService = lessonService;
        this.lessonMapper = lessonMapper;
    }

    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> getCreateLessonPage(@RequestParam int topicId) {
        if(!topicService.topicExistsById(topicId)) {
            throw new BadRequestException("Topic with id " + topicId + " does not exist");
        }

        return ResponseEntity.ok(Map.of(
                "topicId", topicId,
                "userInfo", getUserInfo(),
                "title", "Tạo bài học",
                "redirectUrl", "learningmaterial/lesson/create"
        ));
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createLesson(@RequestBody @Valid LessonCreateDto lessonCreateDto,
                                     Errors errors) {
        try {
            validateUserInput(errors);
            lessonService.createLesson(lessonCreateDto);
            return ResponseEntity.created(URI.create("/admin/topic/" + lessonCreateDto.getTopicId()))
                    .body(Map.of("message", "Create lesson successfully"));
        } catch (UserInputException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message", "Cannot create lesson",
                            "data", Map.of(
                                    "submittedLesson", lessonCreateDto,
                                    "userInputErrors", e
                            )
                    ));
        }
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Map<String, Object>> getLessonPage(@PathVariable int lessonId) {
        try {
            LessonDto lessonDto = lessonService.getLessonById(lessonId);
            String introduction = lessonService.getLessonIntroduction(lessonId);

            return ResponseEntity.ok(Map.of(
                    "lesson", lessonDto,
                    "userInfo", getUserInfo(),
                    "introduction", introduction,
                    "title", "Chi tiết bài học"
            ));
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot get lesson with id " + lessonId);
        } catch (Exception e) {
            throw new BadRequestException("Cannot get lesson with id " + lessonId);
        }
    }

    @PostMapping("/{lessonId}/update")
    public ResponseEntity<Map<String, Object>> updateLesson(@PathVariable int lessonId,
                                     @RequestBody @Valid LessonUpdateDto lessonUpdateDto,
                                     Errors errors) {
        try {
            validateUserInput(errors);
            lessonService.updateLesson(lessonId, lessonUpdateDto);
        } catch (UserInputException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message", "Cannot update lesson",
                            "data", Map.of(
                                    "submittedLesson", lessonUpdateDto,
                                    "userInputErrors", e
                            )
                    ));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Update lesson successfully",
                "data", lessonService.getLessonById(lessonId)
        ));
    }

    @PostMapping("/{lessonId}/set-publicity")
    public ResponseEntity<Map<String, Object>> setLessonPublicity(@PathVariable int lessonId,
                                           @RequestParam boolean isPublic) {
        try {
            lessonService.setLessonPublicity(lessonId, isPublic);
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot find lesson with id " + lessonId);
        } catch (Exception e) {
            throw new BadRequestException("Cannot set publicity");
        }

        return ResponseEntity.ok(Map.of(
                "message", "Set publicity successfully",
                "data", lessonService.getLessonById(lessonId)
        ));
    }

    @PostMapping("/{lessonId}/delete")
    public ResponseEntity<Map<String, Object>> deleteLesson(@PathVariable int lessonId) {
        try {
            int topicId = lessonService.getTopicIdOfLesson(lessonId);
            lessonService.deleteLesson(lessonId);

            return ResponseEntity.ok(Map.of(
                    "message", "Delete lesson successfully",
                    "data", Map.of(
                            "redirectUrl", "/admin/topic/" + topicId
                    )
            ));
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot find lesson with id " + lessonId);
        } catch (Exception e) {
            throw new BadRequestException("Cannot delete lesson");
        }
    }

    @PostMapping("/{lessonId}/update-introduction")
    public ResponseEntity<Map<String, Object>> updateLessonIntroduction(@PathVariable int lessonId,
                                                 @RequestParam String content) {
        try {
            lessonService.updateLessonIntroduction(lessonId, content);
        } catch (NotFoundException e) {
            throw new NotFoundException("Cannot find lesson with id " + lessonId);
        } catch (Exception e) {
            throw new BadRequestException("Cannot update lesson introduction");
        }

        return ResponseEntity.ok(Map.of(
                "message", "Update lesson introduction successfully",
                "data", lessonService.getLessonById(lessonId)
        ));
    }

    private void validateUserInput(Errors errors) {
        List<InputErrorInfo> errorInfos = errors.getFieldErrors().stream()
                .map(error -> new InputErrorInfo(error.getField(), error.getDefaultMessage()))
                .toList();

        if (!errorInfos.isEmpty()) {
            throw new UserInputException(errorInfos);
        }
    }

    private UserInfo getUserInfo() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return UserInfo.builder()
                .username(authentication.getName())
                .build();
    }
}
